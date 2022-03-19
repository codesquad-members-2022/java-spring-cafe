package com.kakao.cafe.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article {

    private int id;
    private final String writer;
    private final String title;
    private final String contents;
    private LocalDateTime createdTime;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        createdTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedTime(LocalDateTime createdTime) { this.createdTime = createdTime; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isSameId(int referId) {
        return this.id == referId;
    }
}
