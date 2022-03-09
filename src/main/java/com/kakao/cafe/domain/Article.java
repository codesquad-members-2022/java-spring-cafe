package com.kakao.cafe.domain;

import com.kakao.cafe.controller.ArticleForm;

public class Article {
    private int id;
    private String title;
    private String contents;

    public Article(ArticleForm form) {
        this.title = form.getTitle();
        this.contents = form.getContents();
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
