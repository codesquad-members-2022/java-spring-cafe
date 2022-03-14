package com.kakao.cafe.domain;

import com.kakao.cafe.controller.ArticleForm;

import java.time.LocalDateTime;

public class Article {
    private int id;
    private String title;
    private String author;
    private String contents;
    private LocalDateTime writtenTime;

    public Article(ArticleForm form) {
        this.title = form.getTitle();
        this.contents = form.getContents();
        this.author = form.getAuthor();
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getAuthor() { return author; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWrittenTime() {
        this.writtenTime = LocalDateTime.now();
    }


}
