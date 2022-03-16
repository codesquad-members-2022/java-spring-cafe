package com.kakao.cafe.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reply {
    private Long id;
    private Long parentArticleId;
    private String userId;
    private String contents;
    private LocalDateTime localDateTime;

    public Reply() {
    }

    public Reply(Long parentArticleId, String userId, String contents, LocalDateTime localDateTime) {
        this.parentArticleId = parentArticleId;
        this.userId = userId;
        this.contents = contents;
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentArticleId() {
        return parentArticleId;
    }

    public String getUserId() {
        return userId;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reply reply = (Reply) o;
        return Objects.equals(id, reply.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
