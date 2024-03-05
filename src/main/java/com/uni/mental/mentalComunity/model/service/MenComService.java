package com.uni.mental.mentalComunity.model.service;

import com.uni.mental.mentalComunity.model.dao.MenComDAO;
import com.uni.mental.mentalComunity.model.dto.MenComDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class MenComService {

    private final MenComDAO menComDAO;


    public MenComService(MenComDAO menComDAO) {
        this.menComDAO = menComDAO;
    }

    public int registMenCom(MenComDTO menCom) throws Exception {
        int result = menComDAO.registMenCom(menCom);

        if(result <=0){
            throw new Exception("게시물 등록 실패");
        }
        return result;
    }


    public List<MenComDTO> findAllView() {

        return menComDAO.findAllView();
    }


    public MenComDTO selectOne(int no) {
        return menComDAO.selectOne(no);
    }


    public int updateMencom(MenComDTO menCom) throws Exception {
        int result = menComDAO.updateMencom(menCom);

        if(result <=0){
            throw new Exception("게시물 수정 실패");
        }
        return result;
    }


    public int deleteMencom(int no) {

        return menComDAO.deleteMencom(no);
    }

    public int updateViews(int no) {

        return menComDAO.updateViews(no);
    }


    public List<MenComDTO> findAllCate(int cate) {

        return menComDAO.findAllCate(cate);
    }

    public String findImageFileNameById(int no) {

        return menComDAO.findImageFileNameById(no);
    }
}

