package com.kakao.cafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {

  private String writer;
  private String title;
  private String contents;
  private String createdTime;
  private LocalDateTime createdAt;

  public Article(String writer, String title, String contents) {
    this.writer = writer;
    this.title = title;
    this.contents = contents;
    this.createdAt = LocalDateTime.now();
    this.createdTime = this.createdAt.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h시 m분 s초"));
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

  public String getCreatedTime() {
    return createdTime;
  }
}
