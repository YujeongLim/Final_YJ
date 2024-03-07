package com.uni.mental.mapper;

import org.apache.ibatis.annotations.*;
import com.uni.mental.agecomunity.model.dto.AgeCmtDTO;
import java.util.List;

@Mapper
public interface X_AgeCmtMapper {

    @Insert("INSERT INTO TBL_COMM_AGE_COMMENT (AGECOM_NO, MEMBER_NICK, AGECMT_DETAIL, AGECMT_DATE) " +
            "VALUES (#{ageComNo}, #{memberNick}, #{ageCmtDetail}, NOW())")
    void insertComment(AgeCmtDTO comment);

    @Select("SELECT * FROM TBL_COMM_AGE_COMMENT WHERE AGECOM_NO = #{ageComNo}")
    List<AgeCmtDTO> selectCommentsByAgeComNo(Long ageComNo);

    @Delete("DELETE FROM TBL_COMM_AGE_COMMENT WHERE AGECMT_NO = #{ageCmtNo}")
    void deleteComment(Long ageCmtNo);

    @Update("UPDATE TBL_COMM_AGE_COMMENT SET AGECMT_DETAIL = #{ageCmtDetail} WHERE AGECMT_NO = #{ageCmtNo}")
    void updateComment(AgeCmtDTO comment);
}
