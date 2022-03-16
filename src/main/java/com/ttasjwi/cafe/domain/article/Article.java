package com.ttasjwi.cafe.domain.article;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article {

    private Long articleId;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDateTime;

    private Article(ArticleBuilder articleBuilder) {
        this.articleId = articleBuilder.articleId;
        this.title = articleBuilder.title;
        this.content = articleBuilder.content;
        this.writer = articleBuilder.writer;
        this.regDateTime = articleBuilder.regDateTime;
    }

    public static ArticleBuilder builder() {
        return new ArticleBuilder();
    }

    public static class ArticleBuilder {
        private Long articleId;
        private String title;
        private String content;
        private String writer;
        private LocalDateTime regDateTime;

        private ArticleBuilder() {}

        public ArticleBuilder articleId(Long articleId) {
            this.articleId = articleId;
            return this;
        }

        public ArticleBuilder title(String titile) {
            this.title = titile;
            return this;
        }

        public ArticleBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ArticleBuilder writer(String writer) {
            this.writer = writer;
            return this;
        }

        public ArticleBuilder regDateTime(LocalDateTime regDateTime) {
            this.regDateTime = regDateTime;
            return this;
        }

        public Article build() {
            return new Article(this);
        }
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public LocalDateTime getRegDateTime() {
        return regDateTime;
    }

    void initArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return articleId.equals(article.articleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId);
    }

}
