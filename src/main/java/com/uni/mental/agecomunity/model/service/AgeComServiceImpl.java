package com.uni.mental.agecomunity.model.service;

import com.uni.mental.agecomunity.model.dao.AgeComDAO;
import com.uni.mental.agecomunity.model.dto.AgeComDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class AgeComServiceImpl implements AgeComService {

    private final AgeComDAO ageComDAO;
    private final Path rootLocation = Paths.get("src/main/resources/static/attach");

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
    public int updateAgeCom(AgeComDTO ageComDTO, MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            // 파일 처리 로직
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String storedFileName = UUID.randomUUID().toString().replace("-", "") + extension;
            Path destinationFile = rootLocation.resolve(Paths.get(storedFileName)).normalize().toAbsolutePath();

            try {
                file.transferTo(destinationFile.toFile());
                ageComDTO.setAttachNewname(storedFileName); // 새로운 파일 이름 설정
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 실패: " + storedFileName, e);
            }
        }

        int result = ageComDAO.updateAgeCom(ageComDTO); // 첨부파일 이름 포함하여 게시글 업데이트

        if (result <= 0) {
            throw new Exception("게시물 수정 실패");
        }
        return result;
    }

    @Override
    public int deleteAgeCom(int no) {
        return ageComDAO.deleteAgeCom(no);
    }

    //페이징
    @Override
    public List<AgeComDTO> findAllViewByPage(Integer cateNo, int page, int size) {
        int offset = page * size;
        if (cateNo != null) {
            return ageComDAO.findAllViewByPage(cateNo, offset, size);
        } else {
            return ageComDAO.findAllViewByPage(null, offset, size); // cateNo가 null인 경우도 처리
        }
    }



    //전체 게시글수 개수 조회
    @Override
    public int getTotalCount() {
        return ageComDAO.countAll();
    }

    // 카테고리별 게시글 총 개수 조회
    @Override
    public int getTotalCountByCateNo(int cateNo) {
        return ageComDAO.getTotalCountByCateNo(cateNo);
    }
}
