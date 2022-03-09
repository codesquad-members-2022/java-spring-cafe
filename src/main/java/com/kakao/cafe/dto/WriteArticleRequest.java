package com.kakao.cafe.dto;

import com.kakao.cafe.domain.Article;

public class WriteArticleRequest {

    private String writer;
    private String title;
    private String contents;

    public WriteArticleRequest(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article convertToArticle() {
        return new Article(writer, title, contents);
    }

    @Override
    public String toString() {
        return "WriteArticleRequest{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
