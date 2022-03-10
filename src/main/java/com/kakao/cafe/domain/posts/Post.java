package com.kakao.cafe.domain.posts;

import java.time.LocalDateTime;

public class Post {
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime localDateTime;

    public Post(Long id, String writer, String title, String contents, LocalDateTime localDateTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
