package kr.codesquad.cafe.article;

import org.springframework.util.Assert;

import java.time.LocalDateTime;

public class Article {

    private Long id;
    private LocalDateTime timestamp;
    private String writerUserId;
    private String writerName;
    private String title;
    private String contents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getWriterUserId() {
        return writerUserId;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public void setWriterUserId(String writerUserId) {
        Assert.hasText(writerUserId, "작성자는 공백이어선 안 됩니다.");
        this.writerUserId = writerUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Assert.hasText(title, "제목은 공백이어선 안 됩니다.");
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        Assert.hasText(contents, "글 내용은 공백이어선 안 됩니다.");
        this.contents = contents;
    }

    public boolean hasId() {
        return id != null;
    }
}
