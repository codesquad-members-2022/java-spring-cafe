package com.kakao.cafe.Controller.dto;

import com.kakao.cafe.domain.Article;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class ArticleForm {

    @NotEmpty(message = "필수 입력사항입니다.")
    private String writer;
    @NotEmpty(message = "필수 입력사항입니다.")
    private String title;
    private String contents;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public ArticleForm() {
    }

    public ArticleForm(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public static Article toEntity(ArticleForm articleForm) {
        return new Article(
                articleForm.getWriter(),
                articleForm.getTitle(),
                articleForm.getContents(),
                articleForm.getCreatedTime(),
                articleForm.getUpdatedTime());
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
