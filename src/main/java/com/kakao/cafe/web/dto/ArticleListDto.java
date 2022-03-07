package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.Article;

public class ArticleListDto {

    private int articleNum;
    private final String writer;
    private final String title;
    private final String createdTime;

    public ArticleListDto(Article article) {
        this.writer = article.getWriter();
        this.title = article.getTitle();
        this.createdTime = article.getCreatedTime();
    }

    public void setArticleNum(int articleNum) {
        this.articleNum = articleNum;
    }
}
