package com.kakao.cafe.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article {

    private Integer id;
    private final String writer;
    private final String title;
    private final String text;
    private final LocalDateTime createdTime;

    public Article(ArticleWriteRequest articleWriteRequest) {
        writer = articleWriteRequest.getWriter();
        title = articleWriteRequest.getTitle();
        text = articleWriteRequest.getText();
        createdTime = LocalDateTime.now();
    }


    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
