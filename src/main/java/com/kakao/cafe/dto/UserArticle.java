package com.kakao.cafe.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserArticle {

    private Integer id;
    private String writer;
    private String title;
    private String contents;
    private String createdDate;

    public UserArticle(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreatedDate(Date date) {
        String createdDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
        this.createdDate = createdDate;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
