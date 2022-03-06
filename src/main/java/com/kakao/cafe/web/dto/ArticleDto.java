package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.article.Article;

import java.util.Objects;

public class ArticleDto {

    private String writer;
    private String title;
    private String contents;

    public ArticleDto(Article article) {
        this.writer = article.getWriter();
        this.title = article.getTitle();
        this.contents = article.getContents();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleDto that = (ArticleDto) o;
        return Objects.equals(writer, that.writer) && Objects.equals(title, that.title) && Objects.equals(contents, that.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(writer, title, contents);
    }
}
