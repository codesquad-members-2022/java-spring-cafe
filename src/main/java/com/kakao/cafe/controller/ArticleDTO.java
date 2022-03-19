package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import java.time.LocalDateTime;

public class ArticleDTO {

  private String writer;
  private String title;
  private String contents;
  private String createdTime;
  private LocalDateTime createdAt;


  private ArticleDTO(String writer, String title, String contents, String createdTime,
      LocalDateTime createdAt
  ) {
    this.writer = writer;
    this.title = title;
    this.contents = contents;
    this.createdTime = createdTime;
    this.createdAt = createdAt;
  }

  public static ArticleDTO from(Article article) {
    return new ArticleDTO(article.getWriter(), article.getTitle(), article.getContents(),
        article.getCreatedTime(), article.getCreatedAt());
  }

  public String getWriter() {
    return writer;
  }

  public void setWriter(String writer) {
    this.writer = writer;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public LocalDateTime getLocalDateTime() {
    return createdAt;
  }

  public void setLocalDateTime(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(String createdTime) {
    this.createdTime = createdTime;
  }
}
