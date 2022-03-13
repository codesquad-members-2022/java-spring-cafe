package com.kakao.cafe.entity;

import com.kakao.cafe.domain.Article;

import java.time.LocalDate;

public class ArticleEntity {

    private long id;
    private String writer;
    private String title;
    private String contents;
    private LocalDate createDate;

    public ArticleEntity() {}

    public ArticleEntity(long id, String writer, String title, String contents, LocalDate createDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = createDate;
    }

    public static ArticleEntity of(Article article) {
        return new ArticleEntity(article.getId(), article.getWriter(), article.getTitle(),
                article.getContents(), article.getCreateDate());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
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
