package com.kakao.cafe.Controller.dto;

import com.kakao.cafe.domain.Article;

import java.time.format.DateTimeFormatter;

public class ArticleResponse {

    private Long id;
    private String writer;
    private String title;
    private String contents;
    private String createdTime;
    private String lastModifiedTime;
    private final String articleListDateFormat = "yyyy-MM-dd HH:MM";

    public ArticleResponse() {
    }

    public ArticleResponse(Long id, String writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public ArticleResponse toArticleResponse(Article article) {
        ArticleResponse articleResponse = new ArticleResponse(article.getId(), article.getWriter(), article.getTitle(), article.getContents());
        articleResponse.setCreatedTime(article.getCreatedTime().format(DateTimeFormatter.ofPattern(articleListDateFormat)));
        articleResponse.setLastModifiedTime(article.getLastModifiedTime().format(DateTimeFormatter.ofPattern(articleListDateFormat)));
        return articleResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
