package com.uni.mental.ageComunity.Controller;

import com.uni.mental.ageComunity.model.service.AgeComService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.uni.mental.ageComunity.model.dao.AgeComDAO;
import com.uni.mental.ageComunity.model.dto.AgeComDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;


@Controller
@RequestMapping("/agecom")
public class AgeComController {
    //로그출력하기!!
    private static final Logger logger = LoggerFactory.getLogger(AgeComController.class);
    private final AgeComDAO ageComDAO;

    // 파일을 저장할 디렉토리 경로를 설정합니다.
    private final Path rootLocation = Paths.get("src/main/resources/static/attach");
    private final AgeComService ageComService;


    @Autowired
    public AgeComController(AgeComDAO ageComDAO, AgeComService ageComService) {
        this.ageComDAO = ageComDAO;
        this.ageComService = ageComService;
    }

    @GetMapping("/AgeComList")
    public String ageComList(@RequestParam(value = "cateNo", required = false) Integer cateNo,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size,
                             Model model) {
        List<AgeComDTO> ageComList;
        int totalCount;

        if (cateNo != null) {
            ageComList = ageComService.findAllViewByPage(cateNo, page, size);
            totalCount = ageComService.getTotalCountByCateNo(cateNo);
        } else {
            ageComList = ageComService.findAllViewByPage(null, page, size); // 서비스 계층에서 모든 게시글 조회하는 메서드 필요
            totalCount = ageComService.getTotalCount(); // 서비스 계층에서 모든 게시글의 총 개수를 조회하는 메서드 필요
        }
        // 모델에 cateNo 추가해서 선택한 카테고리의 span태그에 background 컬러 넣음
        model.addAttribute("cateNo", cateNo);
        
        int totalPages = (int) Math.ceil((double) totalCount / size);

        model.addAttribute("ageComList", ageComList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "agecom/AgeComList";
    }


    @GetMapping("/AgeComEnrollForm")
    public String AgeComEnrollForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberNick = authentication.getName(); // 현재 인증된 사용자의 이름(닉네임) 가져오기

        model.addAttribute("memberNick", memberNick);
        return "agecom/AgeComEnrollForm"; // Thymeleaf 템플릿 이름 반환
    }
    @GetMapping("/AgeComUpdateForm")
    public ModelAndView AgeComUpdateForm(@RequestParam("no") String no, ModelAndView mv){
        AgeComDTO ageComDTO = ageComDAO.selectOne(Integer.parseInt(no));
        mv.addObject("ageComDTO",ageComDTO);
        mv.setViewName("agecom/AgeComUpdateForm");
        return mv;
    }
    @GetMapping("/AgeComDetailView")
    public ModelAndView AgeComDetailView(@RequestParam("no") String no) {
        // no 파라미터를 정수형으로 변환
        int ageComNo = Integer.parseInt(no);

        // 조회수 업데이트 로직 실행
        ageComDAO.updateViewCount(ageComNo);

        AgeComDTO ageComDTO = ageComDAO.selectOne(ageComNo);
        ModelAndView mv = new ModelAndView();
        mv.addObject("ageComDTO", ageComDTO);
        mv.setViewName("agecom/AgeComDetailView");
        return mv;
    }

    @PostMapping("/regist")
    public String AgeComRegist(@ModelAttribute @Valid AgeComDTO ageComDTO, BindingResult result, @RequestParam("file") MultipartFile file) throws Exception {
        if (result.hasErrors()) {
            logger.error("AgeCom 등록 중 유효성 검사에 실패했습니다: {}", result.getAllErrors());
            return "redirect:/error";
        }

        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String storedFileName = UUID.randomUUID().toString().replace("-", "") + extension;
            Path destinationFile = rootLocation.resolve(Paths.get(storedFileName)).normalize().toAbsolutePath();

            try {
                file.transferTo(destinationFile.toFile());
                ageComDTO.setAttachNewname(storedFileName); // 저장된 파일 이름을 DTO에 설정
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 실패: " + storedFileName, e);
            }
        }

        ageComDAO.registAgeCom(ageComDTO);
        return "redirect:/agecom/AgeComList";
    }




    @PostMapping("/update")
    public String AgeComUpdate(@ModelAttribute @Valid AgeComDTO ageComDTO, BindingResult result, @RequestParam(name = "file", required = false) MultipartFile file) throws Exception {
        if (result.hasErrors()) {
            logger.error("게시글 수정 중 유효성 검사 실패: {}", result.getAllErrors());
            return "redirect:/error";
        }

        ageComService.updateAgeCom(ageComDTO, file); // 첨부파일 포함하여 업데이트
        return "redirect:/agecom/AgeComList";
    }

    @PostMapping("/delete")
    public String AgeComDelete(@RequestParam("no") int no){
        ageComDAO.deleteAgeCom(no);
        return "redirect:/agecom/AgeComList";
    }


}