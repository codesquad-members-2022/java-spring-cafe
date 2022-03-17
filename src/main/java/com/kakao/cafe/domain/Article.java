package com.kakao.cafe.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Article {
    private  int id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;

/*    public Article(String writer, String title, String content) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = LocalDate.now();
    }*/

    public int getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

}
