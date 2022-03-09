package com.kakao.cafe.controller;

public class ArticleForm {
    private String title;
    private String contents;

    public ArticleForm(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
