package com.kakao.cafe.web.domain.article;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {

	private Long id;
	private final String writer;
	private String title;
	private String content;
	private String localDateTime;

	public Article(String writer, String title, String content) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd  hh:mm"));
	}

	public Long getId() {
		return id;
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

	public String getLocalDateTime() {
		return localDateTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Article{" +
			"id=" + id +
			", writer='" + writer + '\'' +
			", title='" + title + '\'' +
			", content='" + content + '\'' +
			", localDateTime='" + localDateTime + '\'' +
			'}';
	}

}
