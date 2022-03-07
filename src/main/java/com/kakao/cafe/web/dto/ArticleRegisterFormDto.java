package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.Article;

public class ArticleRegisterFormDto {

    private final String writer;
    private final String title;
    private final String contents;

    public ArticleRegisterFormDto(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article toEntity() {
        return new Article.ArticleBuilder()
            .setWriter(writer)
            .setTitle(title)
            .setContents(contents)
            .build();
    }

    @Override
    public String toString() {
        return "ArticleRegisterFormDto{" +
            "writer='" + writer + '\'' +
            ", title='" + title + '\'' +
            ", contents='" + contents + '\'' +
            '}';
    }
}
