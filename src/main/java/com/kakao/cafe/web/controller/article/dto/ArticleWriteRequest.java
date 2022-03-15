package com.kakao.cafe.web.controller.article.dto;

import com.kakao.cafe.core.domain.article.Article;

import java.time.LocalDateTime;


public class ArticleWriteRequest {

    private static final Integer ZERO = 0;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
    private int viewCount;

    public ArticleWriteRequest(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public Article toEntity() {
        LocalDateTime createAt = getRandomLocalDateTime();
        return new Article.Builder()
                .id(null)
                .title(title)
                .content(content)
                .writer(writer)
                .createAt(createAt)
                .lastModifiedAt(null)
                .viewCount(ZERO)
                .build();
    }

    private LocalDateTime getRandomLocalDateTime() {
        return LocalDateTime.now();
    }
}
