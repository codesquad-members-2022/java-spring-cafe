package com.kakao.cafe.web.dto;


import com.kakao.cafe.domain.reply.Reply;

public class ReplyDto {

    private final Integer articleId;
    private final String contents;

    public ReplyDto(Integer articleId, String contents) {
        this.articleId = articleId;
        this.contents = contents;
    }

    public Reply toEntityWithWriter(String writer) {
        return Reply.newInstance(this.articleId, writer, this.contents);
    }

    public Integer getArticleId() {
        return articleId;
    }

    public String getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "ReplyDto{" +
                "articleId=" + articleId +
                ", contents='" + contents + '\'' +
                '}';
    }
}
