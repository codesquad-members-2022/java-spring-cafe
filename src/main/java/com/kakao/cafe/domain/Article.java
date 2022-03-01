package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class Article {

    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdDate;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = LocalDateTime.now();
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return contents;
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
            && contents.equals(article.contents);
    }

}
