package com.kakao.cafe.domain;

import java.time.LocalDateTime;
import java.util.Optional;

public class Article extends BaseModel {

    private Long id;
    private String writer;
    private String title;
    private String contents;

    public Article(Long id, String writer, String title, String contents, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        super.setCreatedTime(Optional.ofNullable(createdTime).orElse(LocalDateTime.now()));
        super.setUpdatedTime(Optional.ofNullable(updatedTime).orElse(LocalDateTime.now()));
    }

    public Article(String writer, String title, String contents, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        super.setCreatedTime(Optional.ofNullable(createdTime).orElse(LocalDateTime.now()));
        super.setUpdatedTime(Optional.ofNullable(updatedTime).orElse(LocalDateTime.now()));
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

    public boolean isSameArticle(Long id) {
        return this.id.equals(id);
    }
}
