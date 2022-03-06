package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.article.Article;

public class ArticleDto {

    private String writer;
    private String title;
    private String contents;

    public ArticleDto(Article article) {
        this.writer = article.getWriter();
        this.title = article.getTitle();
        this.contents = article.getContents();
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
