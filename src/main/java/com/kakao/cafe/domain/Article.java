package com.kakao.cafe.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Article {
    private Integer id;
    private User user;
    private String title;
    private List<String> contents;
    private LocalDateTime createDate = LocalDateTime.now();

    public Article(User user, String title, List<String> contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    public boolean isEqualsTitle(String title) {
        return this.title.equals(title);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getContents() {
        return contents;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
