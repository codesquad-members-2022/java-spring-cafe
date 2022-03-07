package com.kakao.cafe.domain;

public class Article {

    private long articleIdx;
    private final String articleTitle;
    private final String articleContent;

    public Article(String articleTitle, String articleContent) {
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
    }

    public long getArticleIdx() {
        return articleIdx;
    }

    public void setArticleIdx(long articleIdx) {
        this.articleIdx = articleIdx;
    }
}
