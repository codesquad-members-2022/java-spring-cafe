package com.kakao.cafe.domain;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Article {

    private int id;
    private User writer;
    private String title;
    private String content;
    private AtomicInteger viewCount;
    private LocalDateTime createdAt;

    public Article(User writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.viewCount = new AtomicInteger();
        this.createdAt = LocalDateTime.now();
    }

    public Article(int id, Article article) {
        this(article.writer, article.title, article.content);
        this.id = id;
    }

    public void addViewCount() {
        this.viewCount.getAndIncrement();
    }

    public int getId() {
        return id;
    }

    public String getWriterNickName() {
        return writer.getUserId();
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
}
