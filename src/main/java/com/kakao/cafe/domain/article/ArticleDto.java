package com.kakao.cafe.domain.article;

public class ArticleDto {

    private final String writer;
    private final String title;
    private final String contents;

    public ArticleDto(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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

    public Article convertToArticle() {
        return new Article(writer, title, contents);
    }
}
