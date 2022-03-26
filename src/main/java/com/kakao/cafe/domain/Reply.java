package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class Reply {

    private Integer replyId;
    private Integer articleId;
    private String userId;
    private String comment;
    private LocalDateTime createdDate;

    public static Reply createWithInput(Integer articleId, String userId, String comment) {
        return new Reply(null, articleId, userId, comment, LocalDateTime.now());
    }

    public Reply(Integer replyId, Integer articleId, String userId, String comment,
        LocalDateTime createdDate) {
        this.replyId = replyId;
        this.articleId = articleId;
        this.userId = userId;
        this.comment = comment;
        this.createdDate = createdDate;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public String getUserId() {
        return userId;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Reply reply = (Reply) o;

        return replyId.equals(reply.replyId);
    }

    @Override
    public String toString() {
        return "Reply{" +
            "replyId=" + replyId +
            ", articleId=" + articleId +
            ", userId='" + userId + '\'' +
            ", comment='" + comment + '\'' +
            ", createdDate=" + createdDate +
            '}';
    }

    public Reply update(String comment) {
        this.comment = comment;
        return this;
    }
}
