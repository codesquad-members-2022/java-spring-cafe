package com.kakao.cafe.domain;

import java.time.LocalDate;

public class Article {
    private  int id;
    private String writer;
    private String title;
    private String content;
    private LocalDate createdDate;

    public Article(String writer, String title, String content) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = LocalDate.now();
    }

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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setId(int id) {
        this.id = id;
    }

}
