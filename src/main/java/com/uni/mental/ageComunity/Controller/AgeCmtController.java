package com.uni.mental.ageComunity.Controller;

import com.uni.mental.ageComunity.model.dto.AgeCmtDTO;
import com.uni.mental.ageComunity.model.service.AgeCmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/agecom")
public class AgeCmtController {

    private final AgeCmtService ageCmtService;

    @Autowired
    public AgeCmtController(AgeCmtService ageCmtService) {
        this.ageCmtService = ageCmtService;
    }

    @PostMapping("/addComment")
    public String addComment(AgeCmtDTO comment) {
        ageCmtService.addComment(comment);
        return "redirect:/agecom/detail/" + comment.getAgeComNo();
    }

    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam("ageCmtNo") Long ageCmtNo, @RequestParam("ageComNo") Long ageComNo) {
        ageCmtService.removeComment(ageCmtNo);
        return "redirect:/agecom/detail/" + ageComNo; // 삭제 후 댓글이 속한 게시글 상세 페이지로 리다이렉트
    }

    @PostMapping("/updateComment")
    public String updateComment(AgeCmtDTO comment) {
        ageCmtService.modifyComment(comment);
        return "redirect:/agecom/detail/" + comment.getAgeComNo(); // 수정 후 댓글이 속한 게시글 상세 페이지로 리다이렉트
    }

}

