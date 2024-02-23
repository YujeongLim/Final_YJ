package com.uni.mental.ageComunity.model.service;

import com.uni.mental.ageComunity.model.dao.AgeComDAO;
import com.uni.mental.ageComunity.model.dto.AgeComDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgeComServiceImpl implements AgeComService {

    private final AgeComDAO ageComDAO;

    public AgeComServiceImpl(AgeComDAO ageComDAO) {
        this.ageComDAO = ageComDAO;
    }

    @Override
    public int registAgeCom(AgeComDTO ageComDTO) throws Exception {
        int result = ageComDAO.registAgeCom(ageComDTO);

        if (ageComDTO.getAttachNewname() != null && !ageComDTO.getAttachNewname().isEmpty()) {
            ageComDAO.insertAttach(ageComDTO); // 첨부파일 정보 저장
        }

        if (result <= 0) {
            throw new Exception("게시물 등록 실패");
        }
        return result;
    }

    @Override
    public List<AgeComDTO> findAllView() {
        return ageComDAO.findAllView();
    }

    @Override
    public AgeComDTO selectOne(int no) {
        return ageComDAO.selectOne(no);
    }

    @Override
    public int updateAgeCom(AgeComDTO ageComDTO) throws Exception {
        // 수정 관련 메소드 호출로 변경
        int result = ageComDAO.updateAgeCom(ageComDTO);

        if (result <= 0) {
            throw new Exception("게시물 수정 실패");
        }
        return result;
    }

    @Override
    public int deleteAgeCom(int no) {
        return ageComDAO.deleteAgeCom(no);
    }
}
