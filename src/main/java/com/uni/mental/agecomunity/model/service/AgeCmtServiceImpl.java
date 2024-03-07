package com.uni.mental.agecomunity.model.service;

import com.uni.mental.agecomunity.model.dao.AgeCmtDAO;
import com.uni.mental.agecomunity.model.dto.AgeCmtDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgeCmtServiceImpl implements AgeCmtService {

    private final AgeCmtDAO ageCmtDAO;

    @Autowired
    public AgeCmtServiceImpl(AgeCmtDAO ageCmtDAO) {
        this.ageCmtDAO = ageCmtDAO;
    }

    @Override
    public List<AgeCmtDTO> getCommentsByAgeComNo(int ageComNo) {
        return ageCmtDAO.selectCommentsByAgeComNo(ageComNo);
    }

    @Override
    public void addComment(AgeCmtDTO comment) {
        ageCmtDAO.insertComment(comment);
    }

    @Override
    public void removeComment(int ageCmtNo) {
        ageCmtDAO.deleteComment(ageCmtNo);
    }

    @Override
    public void modifyComment(AgeCmtDTO comment) {
        ageCmtDAO.updateComment(comment);
    }
}
