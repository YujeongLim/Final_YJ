package com.uni.mental.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CheckupMapper {
    @Select("SELECT MENTAL_NAME FROM TBL_MENTAL_LIST")
    List<String> selectAllMentalNames();

    @Select("SELECT CU_QUESTION FROM TBL_CHECKUP WHERE MENTAL_NAME = #{mentalName}")
    List<String> selectQuestionsByMentalName(String mentalName);

    @Select("SELECT MENTAL_NAME FROM TBL_MENTAL_LIST WHERE MENTAL_NO = #{idx}")
    String selectMentalNameByIdx(int idx);

}
