package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.Article;

public class ArticleDetailDto {

    private final String writer;
    private final String title;
    private final String createdTime;
    private final String contents;

    public ArticleDetailDto(Article article) {
        this.writer = article.getWriter();
        this.title = article.getTitle();
        this.createdTime = article.getCreatedTime();
        this.contents = article.getContents();
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getContents() {
        return contents;
    }
}
