package com.kakao.cafe.web.domain.article;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {

	private static final String DATE_TIME_FORMAT = "yyyy/MM/dd  hh:mm";

	private Long id;
	private final String writer;
	private String title;
	private String content;
	private String localDateTime;

	public Article(String writer, String title, String content) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
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

	public void setLocalDateTime(String localDateTime) {
		this.localDateTime = localDateTime;
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
