package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.reply.Reply;

import java.time.LocalDateTime;

public class ReplyUpdateDto {

    private final Integer id;
    private final Integer articleId;
    private final String writer;
    private final String contents;
    private final LocalDateTime writtenTime;

    public ReplyUpdateDto(Integer id, Integer articleId, String writer, String contents, LocalDateTime writtenTime) {
        this.id = id;
        this.articleId = articleId;
        this.writer = writer;
        this.contents = contents;
        this.writtenTime = writtenTime;
    }

    public Reply toEntity() {
        return Reply.of(this.id, this.articleId, this.writer, this.contents, this.writtenTime);
    }

    public boolean hasSameWriter(String writer) {
        return this.writer.equals(writer);
    }

    public Integer getId() {
        return id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getWrittenTime() {
        return writtenTime;
    }
}
