package com.kakao.cafe.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article {

    private Integer articleId;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdDate;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = LocalDateTime.now();
    }

    public Integer getArticleId() {
        return articleId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return contents;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Article article = (Article) o;

        return Objects.equals(articleId, article.articleId);
    }

    @Override
    public String toString() {
        return "Article{" +
            "articleId=" + articleId +
            ", writer='" + writer + '\'' +
            ", title='" + title + '\'' +
            ", contents='" + contents + '\'' +
            ", createdDate=" + createdDate +
            '}';
    }
}
