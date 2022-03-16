package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class Article {
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private int id;

    public Article(String writer, String title, String content, LocalDateTime createdDate) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = LocalDateTime.now();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
