package com.kakao.cafe.controller.article;

import com.kakao.cafe.domain.Article;

public class ArticleForm {

    private String title;
    private String text;
    private int id;

    public Article toEntity() {
        return new Article(title, text);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
