package com.kakao.cafe.domain.dto;

import javax.validation.constraints.NotBlank;

public class ArticleForm {
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String writer;

    @NotBlank
    private String contents;

    public ArticleForm(@NotBlank String title, @NotBlank String writer, @NotBlank String contents) {
        this.title = title;
        this.writer = writer;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }
}
