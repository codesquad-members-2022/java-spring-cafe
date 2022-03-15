package com.kakao.cafe.dto;

public class ArticleSaveRequest {

    private String writer;
    private String title;
    private String contents;

    private ArticleSaveRequest() {
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public ArticleSaveRequest(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "ArticleSaveRequest{" +
            "writer='" + writer + '\'' +
            ", title='" + title + '\'' +
            ", contents='" + contents + '\'' +
            '}';
    }
}
