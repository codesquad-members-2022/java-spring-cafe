package com.kakao.cafe.domain;

public class Article {

    private int id;
    private String writer;
    private String title;
    private String contents;
    private String createdTime;

    public Article(ArticleBuilder articleBuilder) {
        this.writer = articleBuilder.writer;
        this.title = articleBuilder.title;
        this.contents = articleBuilder.content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
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

    // Builder pattern
    public static class ArticleBuilder {

        private String writer;
        private String title;
        private String content;

        public ArticleBuilder setWriter(String writer) {
            this.writer = writer;
            return this;
        }

        public ArticleBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public ArticleBuilder setContents(String content) {
            this.content = content;
            return this;
        }

        public Article build() {
            return new Article(this);
        }
    }
}
