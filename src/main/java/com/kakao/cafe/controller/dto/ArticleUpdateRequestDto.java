package com.kakao.cafe.controller.dto;

public class ArticleUpdateRequestDto {
    private Long id;
    private String writer;
    private String title;
    private String contents;

    public ArticleUpdateRequestDto(Long id, String writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
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
