package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.article.Article;

import java.time.LocalDateTime;
import java.util.Objects;

public class ArticleResponseDto {

    private final int id;
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

    public LocalDateTime getWrittenTime() {
        return writtenTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleResponseDto that = (ArticleResponseDto) o;
        return id == that.id && Objects.equals(writer, that.writer) && Objects.equals(title, that.title) && Objects.equals(contents, that.contents) && Objects.equals(writtenTime, that.writtenTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, writer, title, contents, writtenTime);
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
