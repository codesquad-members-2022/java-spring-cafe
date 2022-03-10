package com.kakao.cafe.domain;

public class ArticleWriteRequest {
    private String writer;
    private String title;
    private String text;

    public ArticleWriteRequest(String writer, String title, String text) {
        this.writer = writer;
        this.title = title;
        this.text = text;
    }

}
