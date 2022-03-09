package com.kakao.cafe.domain;

import java.time.LocalDate;

public class Article {

    private String id;
    private String writer;
    private String title;
    private String contents;
    private LocalDate createDate;

    public Article(String id, String writer, String title, String contents) {
        this.id = id;
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
