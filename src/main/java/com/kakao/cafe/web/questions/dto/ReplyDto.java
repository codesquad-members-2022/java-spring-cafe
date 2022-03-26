package com.kakao.cafe.web.questions.dto;

public class ReplyDto {
    private String userId;
    private String contents;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
