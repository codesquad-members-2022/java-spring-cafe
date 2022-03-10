package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.Article;
import java.time.LocalDateTime;

public class PostDto {

    private int id;
    private String writerNickname;
    private String title;
    private String content;
    private int viewCount;
    private LocalDateTime createdAt;

    public PostDto(Article post) {
        this.id = post.getId();
        this.writerNickname = post.getWriterNickName();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.viewCount = post.getViewCount();
        this.createdAt = post.getCreatedAt();
    }

    public int getId() {
        return id;
    }

    public String getWriterNickname() {
        return writerNickname;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getViewCount() {
        return viewCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
