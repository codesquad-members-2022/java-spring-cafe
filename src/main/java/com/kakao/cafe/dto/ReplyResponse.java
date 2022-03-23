package com.kakao.cafe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.kakao.cafe.domain.Reply;
import java.time.LocalDateTime;

public class ReplyResponse {

    private Integer replyId;
    private Integer articleId;
    private String userId;
    private String comment;

    @JsonFormat(shape = Shape.STRING)
    private LocalDateTime createdDate;

    public ReplyResponse(Integer replyId, Integer articleId, String userId, String comment,
        LocalDateTime createdDate) {
        this.replyId = replyId;
        this.articleId = articleId;
        this.userId = userId;
        this.comment = comment;
        this.createdDate = createdDate;
    }

    public static ReplyResponse from(Reply reply) {
        return new ReplyResponse(
            reply.getReplyId(),
            reply.getArticleId(),
            reply.getUserId(),
            reply.getComment(),
            reply.getCreatedDate()
        );
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

    @Override
    public String toString() {
        return "ReplyResponse{" +
            "replyId=" + replyId +
            ", articleId=" + articleId +
            ", userId='" + userId + '\'' +
            ", comment='" + comment + '\'' +
            ", createdDate=" + createdDate +
            '}';
    }
}
