package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class Article {

    private User writer;
    private String title;
    private String content;
    private Long viewCount;
    private LocalDateTime createdAt;

    public Article(User writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.viewCount = 0L;
        this.createdAt = LocalDateTime.now();
    }
}
