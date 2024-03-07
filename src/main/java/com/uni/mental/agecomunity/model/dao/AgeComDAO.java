package com.uni.mental.agecomunity.model.dao;

import com.uni.mental.agecomunity.model.dto.AgeComDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AgeComDAO {

    List<AgeComDTO> findAllView();
    List<AgeComDTO> findByCateNo(int cateNo);
    String findCateNameByCateNo(int cateNo);
    AgeComDTO selectOne(int no);
    int registAgeCom(AgeComDTO ageComDTO);
    int updateAgeCom(AgeComDTO ageComDTO);
    int updateAgeComWithAttach(AgeComDTO ageComDTO);
    int deleteAgeCom(int no);

    void updateViewCount(int ageComNo);

    void insertAttach(AgeComDTO ageComDTO);

    // 페이징 처리를 위한 메서드
    List<AgeComDTO> findAllViewByPage(@Param("cateNo") Integer cateNo, @Param("offset") int offset, @Param("limit") int limit);
    // 전체 게시글 수를 조회하는 메서드
    int countAll();

    // 카테고리별 게시글 총 개수 조회 메서드
    int getTotalCountByCateNo(@Param("cateNo") int cateNo);

}
