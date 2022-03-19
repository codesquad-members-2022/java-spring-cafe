package com.kakao.cafe.dto;

import com.kakao.cafe.domain.Article;
import java.time.LocalDateTime;

public class ArticleRequestDto {

    private final String userId;
    private final String writer;
    private final String title;
    private final String contents;

    public ArticleRequestDto(String userId, String writer, String title, String contents) {
        this.userId = userId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article convertToDomain(int id) {
        return new Article(id, userId, writer, title, contents, LocalDateTime.now());
    }

    public String getUserId() {
        return userId;
    }
}
