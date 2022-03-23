package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.article.Article;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ArticleDto {

    @NotBlank
    private final String title;
    @NotBlank
    private final String contents;

    public ArticleDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Article toEntityWithWriter(String writer) {
        return new Article(null, writer, this.title, this.contents);
    }

    public Article toUpdateEntity(Integer articleId, String writer) {
        return new Article(articleId, writer, this.title, this.contents);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleDto that = (ArticleDto) o;
        return Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getContents(), that.getContents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getContents());
    }

    @Override
    public String toString() {
        return "ArticleDto{" +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
