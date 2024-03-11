package com.uni.mental.authentication.model.dto;

import com.uni.mental.member.model.dto.MemberDto;
import com.uni.mental.member.model.dto.MemberRoleDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CustomUser extends User {

    private int no;
    private String id;
    private String pwd;
    private Date bir;
    private String email;
    private String add;
    private String mental;
    private String nick;
    private List<MemberRoleDTO> memberRoleList;

    public CustomUser(MemberDto member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getId(), member.getPwd(), authorities);
        setDetails(member);
    }

    private void setDetails(MemberDto member) {
        this.no = member.getNo();
        this.id = member.getId();
        this.pwd = member.getPwd();
        this.bir = member.getBir();
        this.email = member.getEmail();
        this.add = member.getAdd();
        this.mental = member.getMental();
        System.out.println("Setting nick: " + member.getNick()); // 할당 전 로깅
        this.nick = member.getNick();
        System.out.println("Nick set to: " + this.nick); // 할당 후 로깅
        this.memberRoleList = member.getMemberRoleList();
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getBir(){return bir;}

    public void setBir(Date bir){this.bir = bir;}

    public String getEmail(){return email;}

    public void setEmail(String email){this.email = email;}

    public String getAdd(){return add;}

    public void setAdd(String add){this.add = add;}

    public String getMental(){return mental;}

    public void setMental(String mental){this.mental = mental;}

    public String getNick(){return nick;}

    public void setNick(String nick){this.nick = nick;}


    public List<MemberRoleDTO> getMemberRoleList() {
        return memberRoleList;
    }

    public void setMemberRoleList(List<MemberRoleDTO> memberRoleList) {
        this.memberRoleList = memberRoleList;
    }


    @Override
    public String toString() {
        return "CustomUser{" +
                "no=" + no +
                ", id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", bir=" + bir +
                ", email='" + email + '\'' +
                ", add='" + add + '\'' +
                ", mental='" + mental + '\'' +
                ", nick='" + nick + '\'' +
                ", memberRoleList=" + memberRoleList +
                '}';
    }

}
