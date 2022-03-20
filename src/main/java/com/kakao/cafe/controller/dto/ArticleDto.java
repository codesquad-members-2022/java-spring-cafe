package com.kakao.cafe.controller.dto;

import javax.validation.constraints.NotBlank;

public class ArticleDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ArticleSaveDto{" +
                "userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", content=" + content +
                '}';
    }
}
