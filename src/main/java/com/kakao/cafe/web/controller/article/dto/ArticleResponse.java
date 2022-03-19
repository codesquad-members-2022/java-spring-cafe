package com.kakao.cafe.web.controller.article.dto;

import com.kakao.cafe.core.domain.article.Article;

import java.time.LocalDateTime;

public class ArticleResponse {

    private Integer id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createAt;
    private int viewCount;

    public ArticleResponse(Integer id, String title, String content, String writer, LocalDateTime createAt, int viewCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createAt = createAt;
        this.viewCount = viewCount;
    }

    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.writer = article.getWriter();
        this.createAt = article.getCreateAt();
        this.viewCount = article.getViewCount();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public static ArticleResponse of(Article findArticle) {
        return new ArticleResponse(findArticle);
    }
}
