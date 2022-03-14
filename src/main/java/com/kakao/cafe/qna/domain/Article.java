package com.kakao.cafe.qna.domain;

import static com.kakao.cafe.common.utils.StringValidator.*;

import java.time.LocalDate;

public class Article {
	private Long id;
	private String writer;
	private String title;
	private String content;
	private LocalDate writingDate;
	private Long cafeUserId;

	public Article(Long id, String writer, String title, String content) {
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.writingDate = LocalDate.now();
	}

	// 새글 작성
	public Article(String writer, String title, String content, Long cafeUserId) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.cafeUserId = cafeUserId;
		this.writingDate = LocalDate.now();
	}

	// from db
	public Article(Long id, String writer, String title, String content, Long cafeUserId, LocalDate writingDate) {
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.cafeUserId = cafeUserId;
		this.writingDate = writingDate;
	}

	public boolean isEquals(Long id) {
		return this.id == id;
	}

	public void changeTitle(String changedTitle) {
		if (!isNullOrBlank(changedTitle)) {
			this.title = changedTitle;
		}
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

	public Long getCafeUserId() {
		return cafeUserId;
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
