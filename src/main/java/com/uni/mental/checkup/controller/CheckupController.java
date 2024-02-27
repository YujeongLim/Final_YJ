package com.uni.mental.checkup.controller;

import com.uni.mental.checkup.model.dto.CheckupResultDTO;
import com.uni.mental.checkup.model.dto.CheckupUserDTO;
import com.uni.mental.checkup.model.dto.MessageResponse;
import com.uni.mental.checkup.model.service.CheckupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("checkup")
public class CheckupController {

    private final CheckupService checkupService;

    @Autowired
    public CheckupController(CheckupService checkupService) {
        this.checkupService = checkupService;
    }

    @GetMapping(value={"/checkup"})
    public String checkup(Model model){
        List<String> mentalNames = checkupService.getAllMentalNames();
        model.addAttribute("mentalNames", mentalNames);
        return "checkup/checkup";
    }
    @GetMapping("/questions")
    @ResponseBody
    public ResponseEntity<List<String>> getQuestionsByMentalNameAjax(@RequestParam("mentalName") String mentalName) {
        List<String> questions = checkupService.getQuestionsByMentalName(mentalName);
        return ResponseEntity.ok(questions); // 질문 목록을 JSON 형태로 반환
    }

    @PostMapping("/calculateResult")
    @ResponseBody
    public ResponseEntity<?> calculateResult(@RequestParam("yesCount") int yesCount, @RequestParam("totalQuestions") int totalQuestions) {
        String result;
        double percentage = ((double) yesCount / totalQuestions) * 100;

        if (percentage >= 80) {
            result = "위험, 전문가와의 상담을 권장합니다.";
        } else if (percentage >= 50) {
            result = "의심, 전문가와의 상담을 권장합니다.";
        } else {
            result = "양호한 상태입니다.";
        }

        return ResponseEntity.ok(new MessageResponse(result)); // result를 MessageResponse 객체에 담아 JSON 형식으로 반환
    }


}
