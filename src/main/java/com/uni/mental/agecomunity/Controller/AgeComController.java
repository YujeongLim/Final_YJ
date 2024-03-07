package com.uni.mental.agecomunity.Controller;

import com.uni.mental.agecomunity.model.dao.AgeComDAO;
import com.uni.mental.agecomunity.model.dto.AgeCmtDTO;
import com.uni.mental.agecomunity.model.dto.AgeComDTO;
import com.uni.mental.agecomunity.model.service.AgeCmtService;
import com.uni.mental.agecomunity.model.service.AgeComService;
import net.coobird.thumbnailator.Thumbnails;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/agecom")
public class AgeComController {
    //로그출력하기!!
    private static final Logger logger = LoggerFactory.getLogger(AgeComController.class);
    private final AgeComDAO ageComDAO;

    // 파일을 저장할 디렉토리 경로를 설정합니다.
    private final Path rootLocation = Paths.get("src/main/resources/static/attach");
    private final AgeComService ageComService;
    public class ImageResizer {
        public static void resizeImage(String inputImagePath, String outputImagePath) throws IOException {
            // 원본 이미지 읽기
            BufferedImage originalImage = ImageIO.read(new File(inputImagePath));

            // 이미지 사이즈 조정
            BufferedImage resizedImage = Thumbnails.of(originalImage)
                    .size(800, 600)
                    .asBufferedImage();

            // 조정된 이미지 저장 (WebP 포맷으로)
            File outputFile = new File(outputImagePath);
            // WebP 포맷을 지원하는 ImageWriter를 사용해야 합니다. 아래 코드는 예시일 뿐 실제로는 작동하지 않습니다.
            ImageIO.write(resizedImage, "png", outputFile);
        }
    }

    @Autowired
    public AgeComController(AgeComDAO ageComDAO, AgeComService ageComService) {
        this.ageComDAO = ageComDAO;
        this.ageComService = ageComService;
        this.ageCmtService = ageCmtService; // 댓글 서비스 초기화
    }

    @Autowired
    private AgeCmtService ageCmtService;


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


    @GetMapping("/AgeComDetailView/{ageComNo}")
    public ModelAndView AgeComDetailView(@PathVariable("ageComNo") int ageComNo) {
        // 조회수 업데이트 로직 실행
        ageComDAO.updateViewCount(ageComNo);

        // 게시글 상세 정보 조회
        AgeComDTO ageComDTO = ageComDAO.selectOne(ageComNo);

        // 댓글 리스트 조회
        List<AgeCmtDTO> comments = ageCmtService.getCommentsByAgeComNo(ageComNo);

        // 로거를 통해 댓글 목록 로깅
        logger.info("Comments: " + comments);

        ModelAndView mv = new ModelAndView();
        mv.addObject("ageComDTO", ageComDTO);
        mv.addObject("comments", comments); // 댓글 리스트 모델에 추가
        mv.setViewName("agecom/AgeComDetailView"); // Thymeleaf 템플릿 파일 이름
        return mv;
    }



    @PostMapping("/regist")
    public String AgeComRegist(@ModelAttribute @Valid AgeComDTO ageComDTO, BindingResult result, @RequestParam("file") MultipartFile file) throws Exception {
        if (result.hasErrors()) {
            return "redirect:/error";
        }

        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String storedFileName = UUID.randomUUID().toString().replace("-", "") + extension;
            Path destinationFile = rootLocation.resolve(Paths.get(storedFileName)).normalize().toAbsolutePath();

            try {
                // 파일을 임시 디렉토리에 저장
                File tempFile = destinationFile.toFile();
                file.transferTo(tempFile);

                // 이미지 크기 조정
                BufferedImage srcImg = ImageIO.read(tempFile);
                // 원하는 크기로 이미지 크기 조정. 여기서는 너비 640, 높이를 자동 조절 (-1)로 설정
                BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, 640);

                // 조정된 이미지를 저장할 파일 경로 생성
                String resizedFileName = storedFileName.replace(extension, "_resized" + extension);
                Path resizedDestinationFile = rootLocation.resolve(Paths.get(resizedFileName)).normalize().toAbsolutePath();

                // 조정된 이미지를 jpg 형식으로 저장
                ImageIO.write(destImg, "jpg", resizedDestinationFile.toFile());

                // DTO에 리사이즈된 이미지 파일 이름 설정
                ageComDTO.setAttachNewname(resizedFileName);

                // 임시 원본 파일 삭제
                Files.delete(destinationFile);
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
