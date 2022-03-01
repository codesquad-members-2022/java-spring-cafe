package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class Article {

    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;

    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = LocalDateTime.now();
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Article article = (Article) o;

        return writer.equals(article.writer)
            && title.equals(article.title)
            && content.equals(article.content);
    }

}
