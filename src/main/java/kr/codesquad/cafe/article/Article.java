package kr.codesquad.cafe.article;

import org.springframework.util.Assert;

import java.time.LocalDateTime;

public class Article {

    private Integer index;
    private LocalDateTime timestamp;
    private String writer;
    private String title;
    private String contents;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        Assert.hasText(writer, "작성자는 공백이어선 안 됩니다.");
        this.writer = writer;
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
}
