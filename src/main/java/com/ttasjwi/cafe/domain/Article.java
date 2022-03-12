package com.ttasjwi.cafe.domain;

import java.time.LocalDate;

public class Article {

    private String title;
    private String content;
    private String writer;
    private LocalDate regDate;

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

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}
