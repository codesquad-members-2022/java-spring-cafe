package com.kakao.cafe.domain;

public class ArticleWriteRequest {
    private final String writer;
    private final String title;
    private final String text;

    public ArticleWriteRequest(String writer, String title, String text) {
        this.writer = writer;
        this.title = title;
        this.text = text;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
