package com.kakao.cafe.domain;

public class Article {
    private long id;
    private String writer;
    private String title;
    private String contents;


    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

}
