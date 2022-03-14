package com.kakao.cafe.domain;

import java.util.Date;

public class Article {

    private int articleId;
    private String subject;
    private String content;
    private String uploadDate;
    private String writer;

    public Article(String writer, String subject, String content) {
        this.subject = subject;
        this.content = content;
        this.uploadDate = new java.sql.Date(new Date().getTime()).toString();
        this.writer = writer;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public boolean isEqualArticle(Article toArticle) {
        if (this.articleId == toArticle.articleId && this.uploadDate.equals(toArticle.uploadDate) && this.content.equals(toArticle.content) && this.writer.equals(toArticle.writer) && this.subject.equals(toArticle.subject)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", uploadDate='" + uploadDate + '\'' +
                ", writer='" + writer + '\'' +
                '}';
    }
}
