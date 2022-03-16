package com.ttasjwi.cafe.controller.response;

import com.ttasjwi.cafe.domain.article.Article;

import java.time.LocalDateTime;

public class ArticleResponse {

    private Long articleId;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDateTime;

    public ArticleResponse(Article article) {
        this.articleId = article.getArticleId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.writer = article.getWriter();
        this.regDateTime = article.getRegDateTime();
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
}
