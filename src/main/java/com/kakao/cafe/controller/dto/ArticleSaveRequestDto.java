package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.article.Article;

public class ArticleSaveRequestDto {
    private String writer;
    private String title;
    private String contents;

    public ArticleSaveRequestDto(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article toEntity() {
        return new Article(writer, title, contents);
    }
}
