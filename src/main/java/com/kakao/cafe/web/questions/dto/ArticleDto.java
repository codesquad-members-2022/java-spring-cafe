package com.kakao.cafe.web.questions.dto;

import javax.validation.constraints.NotBlank;

public class ArticleDto {
    @NotBlank
    private String writer;

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

    public ArticleDto() {
    }

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

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
