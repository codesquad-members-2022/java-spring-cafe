package kr.codesquad.cafe.article;

import org.springframework.util.Assert;

public class Article {

    private Integer id;
    private String writer;
    private String title;
    private String contents;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
