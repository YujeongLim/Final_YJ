package com.uni.mental.mentalComunity.Controller;

import com.uni.mental.mentalComunity.model.dao.MenComDAO;
import com.uni.mental.mentalComunity.model.dto.MenComDTO;
import com.uni.mental.mentalComunity.model.service.MenComService;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/mencom")
public class MenComController {

    private final MenComDAO menComDAO;

    private final ResourceLoader resourceLoader;

    private final MenComService menComService;

    public MenComController(MenComDAO menComDAO, ResourceLoader resourceLoader, MenComService menComService) {
        this.menComDAO = menComDAO;
        this.resourceLoader = resourceLoader;


        this.menComService = menComService;
    }

    @GetMapping("mentalList")
    public void MentalList(Model model,
                            @RequestParam(name="cate",required = false,defaultValue = "7") int cate) {
        List<MenComDTO> cate2 = menComService.findAllCate(cate);
        List<MenComDTO> mentalList = menComService.findAllView();
        System.out.println(cate2);
        model.addAttribute("mentalList", mentalList);
    }


    @GetMapping("mentalEnrollForm")
    public void MentalEnrollForm(){}

    @GetMapping("mentalUpdateForm")
    public ModelAndView MentalUpdateForm(@RequestParam("no") String no, ModelAndView mv){
        MenComDTO mental = menComService.selectOne(Integer.parseInt(no));

        mv.addObject("mental",mental);
        mv.setViewName("mencom/mentalUpdateForm");

        return mv;
    }
    @GetMapping("mentalDetailView")
    public ModelAndView MentalDetailView(@RequestParam("no") String no, ModelAndView mv
                                         ){
        MenComDTO mental = menComService.selectOne(Integer.parseInt(no));
        int updateViews = menComService.updateViews(Integer.parseInt(no));


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        mv.addObject("user",id);
        System.out.println("~~~~~~~~~~~~~~~~~"+id);

        mv.addObject("updateViews",updateViews);
        mv.addObject("mental",mental);

        System.out.println(mental);
        mv.setViewName("mencom/mentalDetailView");

        return mv;
    }

    @PostMapping(value = "regist",produces = MediaType.IMAGE_PNG_VALUE)
    public ModelAndView MentalRegist(
                                     @RequestParam(value = "image",required = false) MultipartFile uploadFile,
                                     @Validated MenComDTO menCom,
                                     BindingResult bindingResult) {

        System.out.println(uploadFile);
        ModelAndView modelAndView = new ModelAndView();

        try {
           
            if (uploadFile != null && !uploadFile.isEmpty()) {
                String imageFileName = UUID.randomUUID().toString() + "_" + uploadFile.getOriginalFilename();
                String uploadDir = resourceLoader.getResource("classpath:/static/image").getFile().getAbsolutePath();
                File file = new File(uploadDir + File.separator + imageFileName);
                uploadFile.transferTo(file);

                menCom.setImage(imageFileName);
                System.out.println(imageFileName);
                System.out.println(uploadDir);


                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String id = authentication.getName();
                menCom.setId(id);


                // 插入数据到数据库
                int result = menComService.registMenCom(menCom);

                if (result > 0) {
                    modelAndView.setViewName("redirect:/mencom/mentalList");
                    modelAndView.addObject("message", "등록 성공");
                } else {
                    modelAndView.setViewName("error");
                    modelAndView.addObject("error", "등록 실패");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
            modelAndView.addObject("error", "등록 실패");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return modelAndView;
    }






    @PostMapping(value = "update",produces = MediaType.IMAGE_PNG_VALUE)
    public String MentalUpdate(@RequestParam(value = "image",required = false) MultipartFile uploadFile,
                               @Validated MenComDTO menCom,
                               BindingResult bindingResult) throws Exception {

        System.out.println(uploadFile);
        try {
            // 이미지 파일이 업로드된 경우에만 처리
            if (uploadFile != null && !uploadFile.isEmpty()) {
                String imageFileName = UUID.randomUUID().toString() + "_" + uploadFile.getOriginalFilename();
                String uploadDir = resourceLoader.getResource("classpath:/static/image").getFile().getAbsolutePath();
                File file = new File(uploadDir + File.separator + imageFileName);
                uploadFile.transferTo(file);

                System.out.println("New Image File: " + imageFileName);
                System.out.println("Upload Directory: " + uploadDir);

                // 기존 이미지 파일 삭제
                int no = menCom.getNo();  // 게시물의 고유한 번호
                String oldImageFileName = menComService.findImageFileNameById(no);
                File oldFile = new File(uploadDir + File.separator + oldImageFileName);

                System.out.println("Old Image File: " + oldImageFileName);
                System.out.println("Old File: " + oldFile);



                menCom.setImage(imageFileName);
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String id = authentication.getName();
            menCom.setId(id);

            // 데이터베이스에 업데이트
            menComService.updateMencom(menCom);

        } catch (IOException e) {
            e.printStackTrace();
            // 예외 처리 - 업로드 실패
            return "error";
        }


        return "redirect:/mencom/mentalList";
    }


@PostMapping("delete")
public String MentalDelete(@RequestParam("no") int no){
    menComService.deleteMencom(no);

    return "redirect:/mencom/mentalList";
}




}
