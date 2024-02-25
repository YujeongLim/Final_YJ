package com.uni.mental.ageComunity.model.dao;

import com.uni.mental.ageComunity.model.dto.AgeCmtDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgeCmtDAO {
    void insertComment(AgeCmtDTO comment);
    List<AgeCmtDTO> selectCommentsByAgeComNo(Long ageComNo);
    void deleteComment(Long ageCmtNo);
    void updateComment(AgeCmtDTO comment);
}
