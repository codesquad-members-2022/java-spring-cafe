package com.kakao.cafe.domain;

import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.Objects;

public class Article {

    private Integer articleId;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdDate;

    private Article() {
    }

    public static class Builder {

        private Integer articleId;
        private String writer;
        private String title;
        private String contents;
        private LocalDateTime createdDate;

        public Builder articleId(Integer articleId) {
            this.articleId = articleId;
            return this;
        }

        public Builder writer(String writer) {
            this.writer = writer;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder contents(String contents) {
            this.contents = contents;
            return this;
        }

        public Builder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Article build() {
            if (writer == null || title == null | contents == null) {
                throw new NotFoundException(ErrorCode.FIELD_NOT_FOUND);
            }

            this.createdDate = LocalDateTime.now();
            return new Article(this);
        }
    }

    private Article(Builder builder) {
        articleId = builder.articleId;
        writer = builder.writer;
        title = builder.title;
        contents = builder.contents;
        createdDate = builder.createdDate;
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

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public boolean equalsId(Integer articleId) {
        if (articleId == null) {
            return false;
        }
        return Objects.equals(this.articleId, articleId);
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
