package com.kakao.cafe.domain;

public class Article {

    private long articleIdx;
    private String articleTitle;
    private String articleContent;

    public long getArticleIdx() {
        return articleIdx;
    }

    public void setArticleIdx(long articleIdx) {
        this.articleIdx = articleIdx;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }
}
