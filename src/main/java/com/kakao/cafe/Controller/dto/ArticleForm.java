package com.kakao.cafe.Controller.dto;

import javax.validation.constraints.NotEmpty;

public class ArticleForm {

    @NotEmpty(message = "필수 입력사항입니다.")
    private String writer;
    @NotEmpty(message = "필수 입력사항입니다.")
    private String title;
    private String contents;
    private String createdTime;
    private String lastModifiedTime;

    public ArticleForm() {
    }

    public ArticleForm(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
