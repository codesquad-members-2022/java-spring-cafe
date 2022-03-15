package com.kakao.cafe.domain.dto;

import com.kakao.cafe.domain.Article;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class ArticleForm {
    @NotBlank
    private String title;

    @NotBlank
    private String writer;

    @NotBlank
    private String contents;

    private LocalDateTime dateTime;

    public ArticleForm(String title, String writer, String contents) {
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.dateTime = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public String getContents() {
        return contents;
    }

    public Article createArticle() {
        return new Article(title, writer, contents);
    }
}
