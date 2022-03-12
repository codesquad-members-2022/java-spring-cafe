package com.kakao.cafe.domain;

import com.kakao.cafe.dto.ArticleResponseDto;
import java.time.LocalDateTime;

public class Article {

    private int id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdDate;

    private Article() {

    }

    public Article(int id, String writer, String title, String contents, LocalDateTime createdDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public ArticleResponseDto convertToDto() {
        return new ArticleResponseDto(id, writer, title, contents, createdDate);
    }
}
