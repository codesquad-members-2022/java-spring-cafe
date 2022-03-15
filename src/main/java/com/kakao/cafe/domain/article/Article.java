package com.kakao.cafe.domain.article;

public class Article {
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private String createdDate;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article(Long id, String writer, String title, String contents, String createdDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
    }

    public Article update(String title, String contents) {
        this.title = title;
        this.contents = contents;
        return this;
    }

    public boolean isTheSameId(Long id) {
        return this.id.equals(id);
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }
}
