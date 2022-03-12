package com.kakao.cafe.dto;

import com.kakao.cafe.domain.Article;

import java.time.LocalDate;

public class NewArticleParam {

    private final String writer;
    private final String title;
    private final String contents;

    public NewArticleParam(String writer, String title, String contents) {
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
        return new Article(0, writer, title, contents, LocalDate.now());
    }

    @Override
    public String toString() {
        return "NewArticleParam{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
