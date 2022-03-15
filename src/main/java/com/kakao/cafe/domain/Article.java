package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class Article {
    private String title;
    private String writer;
    private LocalDateTime dateTime;
    private String contents;
    private int index;


    public Article(String title, String writer, String contents) {
        this.title = title;
        this.writer = writer;
        this.dateTime = LocalDateTime.now();
        this.contents = contents;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index+1;
    }
}
