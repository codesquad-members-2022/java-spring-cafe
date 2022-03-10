package com.kakao.cafe.domain;

import java.util.Date;

public class Article {

    private int articleId;
    private String subject;
    private String content;
    private String uploadDate;
    private String writer;

    public int getArticleId() {
        return articleId;
    }

    public String getSubject() {
        return subject;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }


    public Article(String userId, String subject, String content) {
        this.subject = subject;
        this.content = content;
        this.uploadDate = new java.sql.Date(new Date().getTime()).toString();
        this.writer = userId;
    }

}
