package com.uni.mental.mentalComunity.model.dao;

import com.uni.mental.mentalComunity.model.dto.MenComDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenComDAO {

    int registMenCom(MenComDTO menCom);
    MenComDTO selectOne(int no);

    List<MenComDTO> findAllView();



    int updateMencom(MenComDTO menCom);

    int deleteMencom(int no);



    int updateViews(int no);


    List<MenComDTO> findAllCate(int cate);

    String findImageFileNameById(int no);
}
