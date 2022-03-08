package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class Article {
    private Long id;

    private String title;
    private String writer;
    private String dateTime;
    private String contents;


    public Article(String title, String writer, String contents) {
        this.title = title;
        this.writer = writer;
        this.dateTime = LocalDateTime.now().toString();
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getContents() {
        return contents;
    }
}
