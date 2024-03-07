package com.uni.mental.agecomunity.Controller;

import com.uni.mental.agecomunity.model.dao.AgeComDAO;
import com.uni.mental.agecomunity.model.dto.AgeCmtDTO;
import com.uni.mental.agecomunity.model.service.AgeCmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/agecom")
public class AgeCmtController {

    private final AgeCmtService ageCmtService;
    private final AgeComDAO ageComDAO;

    @Autowired
    public AgeCmtController(AgeCmtService ageCmtService, AgeComDAO ageComDAO) {
        this.ageCmtService = ageCmtService;
        this.ageComDAO = ageComDAO;
    }

    @PostMapping("/addComment")
    public String addComment(AgeCmtDTO comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String memberNick = authentication.getName();
            comment.setMemberNick(memberNick);
            ageCmtService.addComment(comment);
            return "redirect:/agecom/AgeComDetailView/" + comment.getAgeComNo();
        } else {
            return "redirect:/login";
        }
    }




    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam("ageCmtNo") int ageCmtNo, @RequestParam("ageComNo") int ageComNo) {
        ageCmtService.removeComment(ageCmtNo);
        return "redirect:/agecom/AgeComDetailView/" + ageComNo; // 삭제 후 댓글이 속한 게시글 상세 페이지로 리다이렉트
    }

    @PostMapping("/updateComment")
    public String updateComment(AgeCmtDTO comment) {
        ageCmtService.modifyComment(comment);
        return "redirect:/agecom/AgeComDetailView/" + comment.getAgeComNo(); // 수정 후 댓글이 속한 게시글 상세 페이지로 리다이렉트
    }

}

