package com.kakao.cafe.domain.dto;

import com.kakao.cafe.domain.Article;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleForm {
    @NotBlank
    private String title;

    @NotBlank
    private String writer;

    @NotBlank
    private String contents;

    private String dateTime;

    public ArticleForm(String title, String writer, String contents) {
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public ArticleForm(String title, String writer, String contents, String dateTime) {
        this.title = title;
        this.writer = writer;
        this.dateTime = dateTime;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getDateTime() {
        return dateTime;
    }
    public String getContents() {
        return contents;
    }

    public Article createArticle() {
        return new Article(title, writer, contents);
    }
}
