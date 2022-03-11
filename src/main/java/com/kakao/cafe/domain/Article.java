package com.kakao.cafe.domain;

import java.time.LocalDate;

import com.kakao.cafe.exception.ErrorMessage;

public class Article {
    private int id;
    private String title;
    private LocalDate date;
    private String content;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void checkBlankInput() {
        if (this.title.isBlank() || this.content.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.BLANK_INPUT.get());
        }
    }
}
