package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.article.Article;

public class ArticleUpdateDto {

    private final Integer id;
    private final String title;
    private final String contents;

    public ArticleUpdateDto(Integer id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public Article toEntityWithWriter(String writer) {
        return Article.newInstance(this.id, writer, this.title, this.contents);
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
