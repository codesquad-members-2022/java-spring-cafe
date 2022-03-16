package com.kakao.cafe.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ModifiedArticleParam {

    private int id;
    private String writer;
    private String title;
    private String contents;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate;

    public ModifiedArticleParam(int id, String writer, String title, String contents, LocalDate createDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "ModifiedArticleParam{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
