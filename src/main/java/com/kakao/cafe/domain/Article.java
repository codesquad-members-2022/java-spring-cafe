package com.kakao.cafe.domain;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Article {

    private int id;
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

    public Article(int id, Article article) {
        this(article.userId, article.title, article.content);
        this.id = id;
    }

    public void addViewCount() {
        this.viewCount.getAndIncrement();
    }

    public int getId() {
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
}
