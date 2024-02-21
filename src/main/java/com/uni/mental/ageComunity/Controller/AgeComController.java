package com.uni.mental.ageComunity.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import com.uni.mental.ageComunity.model.dao.AgeComDAO;
import com.uni.mental.ageComunity.model.dto.AgeComDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/agecom")
public class AgeComController {
    //로그출력하기!!
    private static final Logger logger = LoggerFactory.getLogger(AgeComController.class);
    private final AgeComDAO ageComDAO;
    // 파일을 저장할 디렉토리 경로를 설정합니다.
    private final Path rootLocation = Paths.get("uploads");

    public AgeComController(AgeComDAO ageComDAO) {
        this.ageComDAO = ageComDAO;
    }

    @GetMapping("/AgeComList")
    public void AgeComList(Model model) {
        List<AgeComDTO> AgeComList = ageComDAO.findAllView();
        //로그출력하기!!
        logger.info("AgeComList: {}", AgeComList);
//        model.addAttribute("AgeComList", AgeComList != null ? AgeComList : new ArrayList<>());
      model.addAttribute("AgeComList", AgeComList);
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
    public String AgeComRegist(@ModelAttribute AgeComDTO ageComDTO){
        ageComDAO.registAgeCom(ageComDTO);
        return "redirect:/agecom/AgeComList";
    }

    @PostMapping("/update")
    public String AgeComUpdate(@ModelAttribute AgeComDTO ageComDTO){
        ageComDAO.updateAgeCom(ageComDTO);
        logger.info("ageComDTO:{}",ageComDTO);
        return "redirect:/agecom/AgeComList";
    }

    @PostMapping("/delete")
    public String AgeComDelete(@RequestParam("no") int no){
        ageComDAO.deleteAgeCom(no);
        return "redirect:/agecom/AgeComList";
    }


}
