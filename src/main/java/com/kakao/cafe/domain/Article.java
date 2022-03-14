package com.kakao.cafe.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article {

    private int id;
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime createdTime;

    public Article(ArticleWriteRequest articleWriteRequest) {
        writer = articleWriteRequest.getWriter();
        title = articleWriteRequest.getTitle();
        contents = articleWriteRequest.getContents();
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
