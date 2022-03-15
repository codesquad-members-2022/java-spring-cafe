package com.kakao.cafe.dto;

import java.time.LocalDateTime;

public class ArticleResponse {

    private Integer articleId;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdDate;

    private ArticleResponse() {
    }

    public ArticleResponse(Integer articleId, String writer, String title, String contents,
        LocalDateTime createdDate) {
        this.articleId = articleId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
    }

    public Integer getArticleId() {
        return articleId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArticleResponse article = (ArticleResponse) o;

        return articleId.equals(article.articleId)
            && writer.equals(article.writer)
            && title.equals(article.title)
            && contents.equals(article.contents);
    }

    @Override
    public String toString() {
        return "ArticleResponse{" +
            "articleId=" + articleId +
            ", writer='" + writer + '\'' +
            ", title='" + title + '\'' +
            ", contents='" + contents + '\'' +
            ", createdDate=" + createdDate +
            '}';
    }
}
