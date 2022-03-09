package com.kakao.cafe.domain.article;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article {

    private int id;
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime writtenTime;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writtenTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getWrittenTime() {
        return writtenTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return getId() == article.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
