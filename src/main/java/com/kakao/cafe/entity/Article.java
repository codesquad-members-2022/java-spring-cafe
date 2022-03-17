package com.kakao.cafe.entity;

import java.time.LocalDateTime;

public class Article {

    private final String writer;
    private final String title;
    private final String contents;
    private LocalDateTime dateOfIssue;
    private int id;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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

    public LocalDateTime getDate() {
        return dateOfIssue;
    }

    public void setDate(LocalDateTime dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSameId(int articleId) {
        return this.id == articleId;
    }

    @Override
    public String toString() {
        return "Article{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", dateOfIssue=" + dateOfIssue +
                ", id=" + id +
                '}';
    }
}
