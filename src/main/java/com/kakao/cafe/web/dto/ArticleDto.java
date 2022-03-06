package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.article.Article;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ArticleDto {

    @NotBlank
    private final String writer;
    @NotBlank
    private final String title;
    @NotBlank
    private final String contents;

    public ArticleDto(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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

    public Article toEntity() {
        return new Article(this.writer, this.title, this.contents);
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

    @Override
    public String toString() {
        return "ArticleDto{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
