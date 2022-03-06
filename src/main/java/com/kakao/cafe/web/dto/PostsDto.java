package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.post.Posts;

public class PostResponseDto {

    private String writer;
    private String title;
    private String contents;

    public PostResponseDto(Posts posts) {
        this.writer = posts.getWriter();
        this.title = posts.getTitle();
        this.contents = posts.getContents();
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
