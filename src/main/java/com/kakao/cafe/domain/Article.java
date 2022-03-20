package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class Article {
    private Integer id;
    private User user;
    private String title;
    private String content;
    private LocalDateTime createDate;

    public Article(User user, String title, String content) {
        this(null, user, title, content, LocalDateTime.now());
    }

    public Article(Integer id, Article article) {
        this(id, article.getUser(), article.getTitle(), article.getContent(), article.getCreateDate());
    }

    public Article(Integer id, User user, String title, String content, LocalDateTime createDate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
    }

    public boolean isEqualsTitle(String title) {
        return this.title.equals(title);
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
