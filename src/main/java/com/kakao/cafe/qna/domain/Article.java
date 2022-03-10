package com.kakao.cafe.qna.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Article {
	private Long id;
	private String writer;
	private String title;
	private String content;
	private LocalDate writingDate;

	public Article(Long id, String writer, String title, String content) {
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.writingDate = LocalDate.now();
	}

	public Article(String writer, String title, String content) {
		this(null, writer, title, content);
	}

	public Article(Long id, String writer, String title, String content, LocalDate writingDate) {
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.writingDate = writingDate;
	}

	public boolean has(Long id) {
		return this.id == id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public LocalDate getWritingDate() {
		return writingDate;
	}

	@Override
	public String toString() {
		return "Article{" +
			"id=" + id +
			", writer='" + writer + '\'' +
			", title='" + title + '\'' +
			", content='" + content + '\'' +
			", writingDate=" + writingDate +
			'}';
	}
}
