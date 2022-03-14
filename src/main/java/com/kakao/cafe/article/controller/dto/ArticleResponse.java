package com.kakao.cafe.article.controller.dto;

import com.kakao.cafe.article.domain.Article;
import com.kakao.cafe.utils.DateUtils;

import java.time.LocalDateTime;

public class ArticleResponse {

    private Long id;
    private String title;
    private String createdDate;
    private long viewCount;

    public ArticleResponse() { }

    public static ArticleResponse of(Article article) {
        ArticleResponse response = new ArticleResponse();
        response.setId(article.getId());
        response.setTitle(article.getTitle());
        response.setCreatedDate(article.getCreatedDate());
        response.setViewCount(article.getViewCount());
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = DateUtils.formatDateTime(createdDate);
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }
}
