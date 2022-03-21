package com.kakao.cafe.domain.article;

import java.time.LocalDateTime;

public class Article {
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeTime;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public void writeWhenCreated(LocalDateTime writeTime) {
        this.writeTime = writeTime;
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

    public LocalDateTime getWriteTime() {
        return writeTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isYourWriter(String userId) {
        return writer.equals(userId);
    }
}
