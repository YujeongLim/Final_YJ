package com.uni.mental.agecomunity.model.dao;

import com.uni.mental.agecomunity.model.dto.AgeCmtDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AgeCmtDAO {

    @Select("SELECT AGECMT_NO, AGECOM_NO, MEMBER_NICK, AGECMT_DETAIL, AGECMT_DATE FROM TBL_COMM_AGE_COMMENT WHERE AGECOM_NO = #{ageComNo}")
    @Results({
            @Result(property = "ageCmtNo", column = "AGECMT_NO"),
            @Result(property = "ageComNo", column = "AGECOM_NO"),
            @Result(property = "memberNick", column = "MEMBER_NICK"),
            @Result(property = "ageCmtDetail", column = "AGECMT_DETAIL"),
            @Result(property = "ageCmtDate", column = "AGECMT_DATE")
    })
    List<AgeCmtDTO> selectCommentsByAgeComNo(int ageComNo);
    @Insert("INSERT INTO TBL_COMM_AGE_COMMENT (AGECOM_NO, MEMBER_NICK, AGECMT_DETAIL, AGECMT_DATE) " +
            "VALUES (#{ageComNo}, #{memberNick}, #{ageCmtDetail}, NOW())")
    void insertComment(AgeCmtDTO comment);

    @Delete("DELETE FROM TBL_COMM_AGE_COMMENT WHERE AGECMT_NO = #{ageCmtNo}")
    void deleteComment(int ageCmtNo);

    @Update("UPDATE TBL_COMM_AGE_COMMENT SET AGECMT_DETAIL = #{ageCmtDetail} WHERE AGECMT_NO = #{ageCmtNo}")
    void updateComment(AgeCmtDTO comment);
}
