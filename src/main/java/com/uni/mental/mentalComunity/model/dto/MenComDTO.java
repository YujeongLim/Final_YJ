package com.uni.mental.mentalComunity.model.dto;


import java.util.Date;

public class MenComDTO {

    private Integer no;

    private String id;

    private String title;

    private String content;
    private Integer views;
    private int cate;

    private Date date;

    private String nick;

    private String image;


    public MenComDTO(Integer no, String id, String title, String content, Integer views, int cate, Date date, String nick,String image ) {
        this.no = no;
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.cate = cate;
        this.date = date;
        this.nick = nick;
        this.image = image;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "MenComDTO{" +
                "no=" + no +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", views=" + views +
                ", cate=" + cate +
                ", date=" + date +
                ", nick='" + nick + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}