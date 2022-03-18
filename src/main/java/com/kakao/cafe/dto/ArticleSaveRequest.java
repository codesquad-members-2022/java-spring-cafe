package com.kakao.cafe.dto;

import com.kakao.cafe.domain.Article;

public class ArticleSaveRequest {

    private String writer;
    private String title;
    private String contents;

    public ArticleSaveRequest(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article toEntity() {
        return new Article(writer, title, contents);
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "ArticleSaveRequest{" +
            "writer='" + writer + '\'' +
            ", title='" + title + '\'' +
            ", contents='" + contents + '\'' +
            '}';
    }
}
