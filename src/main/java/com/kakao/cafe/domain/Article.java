package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class Article {
    private int id;

    private String title;
    private String writer;
    private LocalDateTime dateTime;
    private String contents;


    public Article(String title, String writer, String contents) {
        this.title = title;
        this.writer = writer;
        this.dateTime = LocalDateTime.now();
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getContents() {
        return contents;
    }
}
