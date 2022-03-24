package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.article.Article;

import java.time.LocalDateTime;
import java.util.Objects;

public class ArticleResponseDto {

    private final Integer id;
    private final String writer;
    private final String title;
    private final String contents;
    private final LocalDateTime writtenTime;

    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.writer = article.getWriter();
        this.title = article.getTitle();
        this.contents = article.getContents();
        this.writtenTime = article.getWrittenTime();
    }

    public boolean isSameWriter(String writer) {
        return this.writer.equals(writer);
    }

    public Integer getId() {
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

    public LocalDateTime getWrittenTime() {
        return writtenTime;
    }


    @Override
    public String toString() {
        return "ArticleResponseDto{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", writtenTime=" + writtenTime +
                '}';
    }
}
