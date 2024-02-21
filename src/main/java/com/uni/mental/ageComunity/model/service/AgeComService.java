package com.uni.mental.ageComunity.model.service;

import com.uni.mental.ageComunity.model.dto.AgeComDTO;
import java.util.List;

public interface AgeComService {

    // 게시글 등록
    int registAgeCom(AgeComDTO ageCom) throws Exception;

    // 모든 게시글 조회
    List<AgeComDTO> findAllView();

    // 특정 게시글 조회
    AgeComDTO selectOne(int no);

    // 게시글 수정
    int updateAgeCom(AgeComDTO ageCom) throws Exception;

    // 게시글 삭제
    int deleteAgeCom(int no);
}
