package com.kakao.cafe.dto;

import com.kakao.cafe.domain.Article;
import java.time.LocalDateTime;

public class ArticleRequestDto {

    private String writer;
    private String title;
    private String contents;

    public ArticleRequestDto(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article convertToDomain() {
        return new Article(writer, title, contents, LocalDateTime.now());
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
