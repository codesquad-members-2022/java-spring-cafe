package com.kakao.cafe.controller;

public class ArticleForm {
    private String title;
    private String contents;
    private String author;

    public ArticleForm(String title, String contents, String author) {
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
}
