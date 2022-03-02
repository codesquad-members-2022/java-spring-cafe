package com.kakao.cafe.web.users.dto;

public class ArticleDto {
    private String writer;
    private String title;
    private String contents;

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
}
