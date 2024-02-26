package com.uni.mental.ageComunity.model.service;

import com.uni.mental.ageComunity.model.dto.AgeCmtDTO;
import java.util.List;

public interface AgeCmtService {

    void addComment(AgeCmtDTO comment);

    List<AgeCmtDTO> getCommentsByAgeComNo(int ageComNo);

    void removeComment(int ageCmtNo);

    void modifyComment(AgeCmtDTO comment);
}