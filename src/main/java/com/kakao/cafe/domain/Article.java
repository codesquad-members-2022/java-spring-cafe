package com.kakao.cafe.domain;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Article {

    private Integer id;
    private String userId;
    private String title;
    private String content;
    private AtomicInteger viewCount;
    private LocalDateTime createdAt;

    public Article(String userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.viewCount = new AtomicInteger();
        this.createdAt = LocalDateTime.now();
    }

    public Article(Integer id, Article article) {
        this.id = id;
        this.userId = article.userId;
        this.title = article.title;
        this.content = article.content;
        this.viewCount = article.viewCount;
        this.createdAt = article.createdAt;
    }

    public Article(Integer id, String userId, String title, String content,
        AtomicInteger viewCount, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
    }

    public void addViewCount() {
        this.viewCount.getAndIncrement();
    }

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getViewCount() {
        return viewCount.intValue();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean hasSameId(int id) {
        return this.id == id;
    }

    public boolean isNewArticle() {
        return this.id == null;
    }
}
