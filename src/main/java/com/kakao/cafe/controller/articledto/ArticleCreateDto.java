package com.kakao.cafe.controller.articledto;

public class ArticleCreateDto {

    private final String articleTitle;
    private final String articleContent;

    public ArticleCreateDto(String articleTitle, String articleContent) {
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }
}
