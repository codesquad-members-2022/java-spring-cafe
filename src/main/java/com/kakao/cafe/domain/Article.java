package com.kakao.cafe.domain;

import java.time.LocalDate;

public class Article {

    private String id;
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDate createDate;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDate.now();
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
