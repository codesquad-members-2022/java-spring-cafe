package com.kakao.cafe.dto;

public class ArticleForm {

    private String writer;
    private String title;
    private String contents;

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public ArticleForm(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }
}
