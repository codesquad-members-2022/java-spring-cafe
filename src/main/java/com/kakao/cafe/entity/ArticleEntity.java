package com.kakao.cafe.entity;

import com.kakao.cafe.domain.Article;

import java.time.LocalDate;

public class ArticleEntity {

    private int id;
    private String writer;
    private String title;
    private String contents;
    private LocalDate createDate;

    public ArticleEntity() {}

    public ArticleEntity(Article article) {
        this.id = article.getId();
        this.writer = article.getWriter();
        this.title = article.getTitle();
        this.contents = article.getContents();
        this.createDate = article.getCreateDate();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public long getId() {
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public Article convertToArticle() {
        return new Article(id, writer, title, contents, createDate);
    }

    @Override
    public String toString() {
        return "ArticleResponse{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
