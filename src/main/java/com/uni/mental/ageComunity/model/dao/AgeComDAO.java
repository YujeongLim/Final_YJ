package com.uni.mental.ageComunity.model.dao;

import com.uni.mental.ageComunity.model.dto.AgeComDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgeComDAO {

    List<AgeComDTO> findAllView();
    AgeComDTO selectOne(int no);
    int registAgeCom(AgeComDTO ageComDTO);
    int updateAgeCom(AgeComDTO ageComDTO);

    int deleteAgeCom(int no);

    void updateViewCount(int ageComNo);
}
