package com.ttasjwi.cafe.controller.request;

import com.ttasjwi.cafe.domain.article.Article;

import java.time.LocalDateTime;

public class ArticleWriteRequest {

    private static final String DEFAULT_WRITER = "Anonymous User";

    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDateTime;

    public ArticleWriteRequest(String title, String content) {
        this.title = title;
        this.content = content;
        this.writer = DEFAULT_WRITER;
        this.regDateTime = LocalDateTime.now();
    }

    private ArticleWriteRequest(ArticleWriteRequestBuilder articleWriteRequestBuilder) {
        this.title = articleWriteRequestBuilder.title;
        this.content = articleWriteRequestBuilder.content;
        this.writer = articleWriteRequestBuilder.writer;
        this.regDateTime = articleWriteRequestBuilder.regDateTime;
    }

    public static ArticleWriteRequestBuilder builder() {
        return new ArticleWriteRequestBuilder();
    }

    public static class ArticleWriteRequestBuilder {

        private String title;
        private String content;
        private String writer;
        private LocalDateTime regDateTime;

        private ArticleWriteRequestBuilder() {}

        public ArticleWriteRequestBuilder title(String titile) {
            this.title = titile;
            return this;
        }

        public ArticleWriteRequestBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ArticleWriteRequestBuilder writer(String writer) {
            this.writer = writer;
            return this;
        }

        public ArticleWriteRequestBuilder regDateTime(LocalDateTime regDateTime) {
            this.regDateTime = regDateTime;
            return this;
        }

        public ArticleWriteRequest build() {
            return new ArticleWriteRequest(this);
        }
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

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .regDateTime(regDateTime)
                .build();
    }
}
