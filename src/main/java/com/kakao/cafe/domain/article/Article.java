package com.kakao.cafe.domain.article;

import java.time.LocalDateTime;

public class Article {
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeTime;

    public Article(String writer, String title, String contents, LocalDateTime writeTime) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeTime = writeTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
