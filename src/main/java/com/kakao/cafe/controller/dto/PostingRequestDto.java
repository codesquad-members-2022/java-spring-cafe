package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;

public class PostingRequestDto {

    private User writer;
    private String title;
    private String content;

    public PostingRequestDto(String title, String content) {
        // TODO : 로그인 구현 후 User도 입력받아 설정하기
        this.writer = new User("test@user.com", "testuser", "test");
        this.title = title;
        this.content = content;
    }

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Article toEntity() {
        return new Article(writer, title, content);
    }
}
