package com.kakao.cafe.domain;

import com.kakao.cafe.dto.ArticleResponseDto;
import java.time.LocalDateTime;

public class Article {

    private Integer id;
    private String userId;
    private String writer; // writer = User's name (미션 5단계 프로그래밍 요구사항)
    private String title;
    private String contents;
    private LocalDateTime createdDate;

    private Article() { }

    public Article(Integer id, String userId, String writer, String title, String contents, LocalDateTime createdDate) {
        this.id = id;
        this.userId = userId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
    }

    public Article(String userId, String writer, String title, String contents, LocalDateTime createdDate) {
        this.userId = userId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
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
        return new ArticleResponseDto(id, userId, writer, title, contents, createdDate);
    }
}
