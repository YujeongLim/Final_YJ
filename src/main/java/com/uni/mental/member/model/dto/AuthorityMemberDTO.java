package com.uni.mental.member.model.dto;

public class AuthorityMemberDTO {
    private int no;												// 권한코드
    private String name;											// 권한코드
    private String nick;											// 권한명
    private String desc;											// 권한설명

    public AuthorityMemberDTO() {
    }

    public AuthorityMemberDTO(int no, String name, String nick, String desc) {
        this.no = no;
        this.name = name;
        this.name = nick;
        this.desc = desc;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int code) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String nick() {
        return nick;
    }

    public void setNick(String name) {
        this.nick = nick;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "AuthorityDTO [no=" + no + ", name=" + name + ", nick= "+ nick +", desc=" + desc + "]";
    }

}

