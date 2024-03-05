package com.uni.mental.mentalComunity.model.service;

import com.uni.mental.mentalComunity.model.dao.MenComCmtDAO;
import com.uni.mental.mentalComunity.model.dto.MenComCmtDTO;
import com.uni.mental.mentalComunity.model.dto.MenComDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenComCmtService {

    private final MenComCmtDAO menComCmtDAO;

    public MenComCmtService(MenComCmtDAO menComCmtDAO) {
        this.menComCmtDAO = menComCmtDAO;
    }

    public int deleteComment(int no) {

        return menComCmtDAO.deleteComment(no);
    }

    public List<MenComDTO> mencmtList() {

        return menComCmtDAO.mencmtList();

    }

    public int insertComment(MenComCmtDTO result) throws Exception {

       int comment = menComCmtDAO.insertComment(result);

        if(comment <=0){
            throw new Exception("게시물 수정 실패");
        }
        return comment;
    }

    public List<MenComCmtDTO> getList(MenComCmtDTO result) throws Exception {

        return menComCmtDAO.getList(result);
    }
}
