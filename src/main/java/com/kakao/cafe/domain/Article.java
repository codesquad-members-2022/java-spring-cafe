package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class Article {

  private String writer;
  private String title;
  private String contents;
  private LocalDateTime createdAt;

  public Article(String writer, String title, String contents) {
    this.writer = writer;
    this.title = title;
    this.contents = contents;
    this.createdAt = LocalDateTime.now();
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
