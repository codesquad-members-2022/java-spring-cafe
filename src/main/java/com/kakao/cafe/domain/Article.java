package com.kakao.cafe.domain;

import com.kakao.cafe.controller.ArticleForm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private int id;
    private String title;
    private String author;
    private String contents;
    private LocalDateTime writtenTime;

    public Article(String title, String contents, String author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getAuthor() { return author; }

    public LocalDateTime getWrittenTime() { return writtenTime; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWrittenTime(LocalDateTime localDateTime) {
        this.writtenTime = localDateTime;
    }

    public String getFormattedWrittenTime() {
        return writtenTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


}
