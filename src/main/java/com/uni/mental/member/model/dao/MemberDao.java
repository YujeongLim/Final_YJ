package com.uni.mental.member.model.dao;

import com.uni.mental.member.model.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    MemberDto findMemberById(String memberId);

    void enrollMember(MemberDto memberDto);

    int idCheck(String id);

    int nicknameCheck(String nick);
    MemberDto findByEmail(String email);
}
