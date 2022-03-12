package com.kakao.cafe.core.domain.article;

import java.time.LocalDateTime;

public class Article {

    private int id;
    private String title;
    private String content;

    /**
     * 회원과의 연관 관계는 추후 미션에서
     */
    private String writer;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
    private int viewCount;

    public Article(int id, String title, String content, String writer, LocalDateTime createAt, LocalDateTime lastModifiedAt, int viewCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createAt = createAt;
        this.lastModifiedAt = lastModifiedAt;
        this.viewCount = viewCount;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public int getViewCount() {
        return viewCount;
    }

    public static class Builder {
        private int id;
        private String title;
        private String content;
        private String writer;
        private LocalDateTime createAt;
        private LocalDateTime lastModifiedAt;
        private int viewCount;

        public Builder() {

        }

        public Article.Builder id(int id) {
            this.id = id;
            return this;
        }

        public Article.Builder title(String title) {
            this.title = title;
            return this;
        }

        public Article.Builder content(String content) {
            this.content = content;
            return this;
        }

        public Article.Builder writer(String writer) {
            this.writer = writer;
            return this;
        }

        public Article.Builder createAt(LocalDateTime createAt) {
            this.createAt = createAt;
            return this;
        }

        public Article.Builder lastModifiedAt(LocalDateTime lastModifiedAt) {
            this.lastModifiedAt = lastModifiedAt;
            return this;
        }

        public Article.Builder viewCount(int viewCount) {
            this.viewCount = viewCount;
            return this;
        }

        public Article build() {
            return new Article(id, title, content, writer, createAt, lastModifiedAt, viewCount);
        }
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", createAt=" + createAt +
                ", lastModifiedAt=" + lastModifiedAt +
                ", viewCount=" + viewCount +
                '}';
    }
}
