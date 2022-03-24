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
        return Article.newInstance(null, writer, this.title, this.contents);
    }

    @Override
    public String toString() {
        return "ArticleDto{" +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
