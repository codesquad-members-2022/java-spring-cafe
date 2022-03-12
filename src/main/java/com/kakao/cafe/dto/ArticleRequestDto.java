package com.kakao.cafe.dto;

import com.kakao.cafe.domain.Article;
import java.time.LocalDateTime;

public class ArticleRequestDto {

    private final String writer;
    private final String title;
    private final String contents;

    public ArticleRequestDto(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article convertToDomain() {
        return new Article(0, writer, title, contents, LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "ArticleDto{" +
            "writer='" + writer + '\'' +
            ", title='" + title + '\'' +
            ", contents='" + contents + '\'' +
            '}';
    }
}
