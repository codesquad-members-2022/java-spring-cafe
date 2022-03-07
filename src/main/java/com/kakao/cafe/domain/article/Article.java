package com.kakao.cafe.domain.article;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private Integer id;
    private final String writer;
    private String title;
    private String contents;
    private String writeTime;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
