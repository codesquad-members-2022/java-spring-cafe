package com.kakao.cafe.domain.reply;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reply {

    private Integer id;
    private final Integer ArticleId;
    private final String writer;
    private final String contents;
    private final LocalDateTime writtenTime;

    private Reply(Integer id, Integer articleId, String writer, String contents, LocalDateTime writtenTime) {
        this.id = id;
        ArticleId = articleId;
        this.writer = writer;
        this.contents = contents;
        this.writtenTime = writtenTime;
    }
    public static Reply newInstance(Integer articleId, String writer, String contents) {
        return new Reply(null, articleId, writer, contents, LocalDateTime.now());
    }

    public static Reply of(Integer id, Integer articleId, String writer, String contents, LocalDateTime writtenTime) {
        return new Reply(id, articleId, writer, contents, writtenTime);
    }

    public boolean hasId() {
        return id != null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return ArticleId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reply reply = (Reply) o;
        return getId().equals(reply.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
