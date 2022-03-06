package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class Article {
    private Long id;
    private String userId;
    private String title;
    private String contents;
    private LocalDateTime localDateTime;

    public Article(String userId, String title, String contents, LocalDateTime localDateTime) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
