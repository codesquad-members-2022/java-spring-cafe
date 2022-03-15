package com.ttasjwi.cafe.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Article {

    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDateTime;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
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

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public LocalDateTime getRegDateTime() {
        return regDateTime;
    }

    public void setRegDateTime(LocalDateTime regDateTime) {
        this.regDateTime = regDateTime;
    }

    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", regDateTime=" + regDateTime +
                '}';
    }
}
