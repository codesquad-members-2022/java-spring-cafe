package com.kakao.cafe.dto;

import java.time.LocalDateTime;

public class ArticleResponseDto {

    private int id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdDate;

    public ArticleResponseDto(int id, String writer, String title, String contents, LocalDateTime createdDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
    }

    public int getId() {
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
