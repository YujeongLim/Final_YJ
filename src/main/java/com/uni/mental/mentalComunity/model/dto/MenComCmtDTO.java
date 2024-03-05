package com.uni.mental.mentalComunity.model.dto;

import java.util.Date;
import java.util.Map;

public class MenComCmtDTO {

    private Integer no;

    private String id;

    private Integer menNo;

    private String content;

    private Date date;

    private String nick;

    private Map<String, Object> userInfo;

    public MenComCmtDTO() {

    }

    @Override
    public String toString() {
        return "MenComCmtDTO{" +
                "no=" + no +
                ", id='" + id + '\'' +
                ", menNo=" + menNo +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", nick='" + nick + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMenNo() {
        return menNo;
    }

    public void setMenNo(Integer menNo) {
        this.menNo = menNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Map<String, Object> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Map<String, Object> userInfo) {
        this.userInfo = userInfo;
    }

    public MenComCmtDTO(Integer no, String id, Integer menNo, String content, Date date, String nick, Map<String, Object> userInfo) {
        this.no = no;
        this.id = id;
        this.menNo = menNo;
        this.content = content;
        this.date = date;
        this.nick = nick;
        this.userInfo = userInfo;
    }
}