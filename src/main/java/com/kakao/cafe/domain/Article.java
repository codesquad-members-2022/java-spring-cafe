package com.kakao.cafe.domain;

import java.util.Date;

public class Article {

    private static final Date date = new Date();
    private static int articleCount = 0;

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

    public Article(String userId, String subject, String content) {
        this.articleId = articleCount++;
        this.subject = subject;
        this.content = content;
        this.uploadDate = generateUploadDate();
        this.writer = userId;
    }

    private String generateUploadDate() {
        return date.toString();
    }

}
