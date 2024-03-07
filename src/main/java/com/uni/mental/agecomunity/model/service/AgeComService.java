package com.uni.mental.agecomunity.model.service;

import com.uni.mental.agecomunity.model.dto.AgeComDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AgeComService {

    // 게시글 등록
    int registAgeCom(AgeComDTO ageCom) throws Exception;

    // 모든 게시글 조회
    List<AgeComDTO> findAllView();

    // 특정 게시글 조회
    AgeComDTO selectOne(int no);

    // 게시글 수정
    int updateAgeCom(AgeComDTO ageComDTO, MultipartFile file) throws Exception;
    // 게시글 삭제
    int deleteAgeCom(int no);

    // 페이지네이션 처리를 위한 메서드
    List<AgeComDTO> findAllViewByPage(Integer cateNo, int page, int size);

    // 전체 게시글 수를 조회하는 메서드
    int getTotalCount();

    // 카테고리별 게시글 총 개수 조회 메서드
    int getTotalCountByCateNo(int cateNo);
}
