package com.kakao.cafe.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Article {
	private String writer;
	private String title;
	private String contents;
	private String writtenTime;
	private int id;

	public Article(String writer, String title, String contents, int id) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.writtenTime = createWrittenTime();
		this.id = id;
	}
	public int getId() {
		return id;
	}

	private String createWrittenTime() {
		LocalDateTime seoulNow = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return seoulNow.format(dateTimeFormatter);
	}
}
