package com.kakao.cafe.controller.dto;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleSaveDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String title;

    private List<String> contents;

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = Arrays.stream(contents.split("\n")).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "ArticleSaveDto{" +
                "userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", contents=" + contents +
                '}';
    }
}
