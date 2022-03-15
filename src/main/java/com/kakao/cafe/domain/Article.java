package com.kakao.cafe.domain;

public class Article {

    private int id;
    private String title;
    private String text;

    public Article(int id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
