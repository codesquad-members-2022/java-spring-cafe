package com.kakao.cafe.domain;

import com.kakao.cafe.Controller.dto.ArticleDto;

import java.time.LocalDateTime;

public class Article extends BaseModel {

    private Long id;
    private String writer;
    private String title;
    private String contents;

    public Article(Long id, String writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        super.setCreatedTime(LocalDateTime.now());
        super.setLastModifiedTime(LocalDateTime.now());
    }

    public Article(Long id, ArticleDto articleDto) {
        this.id = id;
        this.writer = articleDto.getWriter();
        this.title = articleDto.getTitle();
        this.contents = articleDto.getContents();
        super.setCreatedTime(LocalDateTime.now());
        super.setLastModifiedTime(LocalDateTime.now());
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
