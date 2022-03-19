package com.kakao.cafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private String title;
    private String writer;
    private String dateTime;
    private String contents;
    private int index;


    public Article(String title, String writer, String contents) {
        this.title = title;
        this.writer = writer;
        this.dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.contents = contents;
    }

    public Article(String title, String writer, String contents, String dateTime) {
        this.title = title;
        this.writer = writer;
        this.dateTime = dateTime;
        this.contents = contents;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
