package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.Article;

public class PostingRequestDto {

    private String userId;
    private String title;
    private String content;

    public PostingRequestDto(String title, String content) {
        // TODO : 로그인 구현 후 User도 입력받아 설정하기
        this.userId = "Anonymous";
        this.title = title;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Article toEntity() {
        return new Article(userId, title, content);
    }
}
