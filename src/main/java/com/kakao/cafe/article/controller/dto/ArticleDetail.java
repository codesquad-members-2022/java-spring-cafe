package com.kakao.cafe.article.controller.dto;

import com.kakao.cafe.article.domain.Article;
import com.kakao.cafe.utils.DateUtils;

public class ArticleDetail {

    private Long id;
    private String title;
    private String content;
    private String createdDate;
    private long viewCount;

    public static ArticleDetail of(Article article) {
        ArticleDetail detail = new ArticleDetail();
        detail.setId(article.getId());
        detail.setTitle(article.getTitle());
        detail.setContent(article.getContent());
        detail.setCreatedDate(DateUtils.formatDateTime(article.getCreatedDate()));
        detail.setViewCount(article.getViewCount());

        return detail;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }
}
