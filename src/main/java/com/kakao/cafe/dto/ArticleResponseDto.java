package com.kakao.cafe.dto;

import java.time.LocalDateTime;

public class ArticleResponseDto {

    private final int id;
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime createdDate;

    public ArticleResponseDto(int id, String writer, String title, String contents, LocalDateTime createdDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
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
