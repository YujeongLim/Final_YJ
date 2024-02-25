package com.uni.mental.ageComunity.model.service;

import com.uni.mental.ageComunity.model.dao.AgeCmtDAO;
import com.uni.mental.ageComunity.model.dto.AgeCmtDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgeCmtService {

    private final AgeCmtDAO ageCmtDAO;

    @Autowired
    public AgeCmtService(AgeCmtDAO ageCmtDAO) {
        this.ageCmtDAO = ageCmtDAO;
    }

    public void addComment(AgeCmtDTO comment) {
        ageCmtDAO.insertComment(comment);
    }

    public List<AgeCmtDTO> getComments(Long ageComNo) {
        return ageCmtDAO.selectCommentsByAgeComNo(ageComNo);
    }

    public void removeComment(Long ageCmtNo) {
        ageCmtDAO.deleteComment(ageCmtNo);
    }

    public void modifyComment(AgeCmtDTO comment) {
        ageCmtDAO.updateComment(comment);
    }
}

