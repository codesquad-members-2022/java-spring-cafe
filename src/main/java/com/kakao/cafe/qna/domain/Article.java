package com.kakao.cafe.qna.domain;

import static com.kakao.cafe.common.utils.StringValidator.*;

import java.time.LocalDate;

public class Article {
	private Long articleId;
	private String writer;
	private String title;
	private String content;
	private LocalDate writingDate;
	private Long cafeUserId;

	public Article(Long articleId, String writer, String title, String content) {
		this.articleId = articleId;
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
	public Article(Long articleId, String writer, String title, String content, Long cafeUserId, LocalDate writingDate) {
		this.articleId = articleId;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.cafeUserId = cafeUserId;
		this.writingDate = writingDate;
	}

	public boolean isEquals(Long id) {
		return this.articleId == id;
	}

	public void changeTitle(String changedTitle) {
		if (!isNullOrBlank(changedTitle)) {
			this.title = changedTitle;
		}
	}

	public void update(ArticleDto.EditRequest updateDto) {
		if (!isNullOrBlank(updateDto.getTitle())) {
			this.title = updateDto.getTitle();
		}
		if (!isNullOrBlank(updateDto.getContents())) {
			this.content = updateDto.getContents();
		}
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
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
			"id=" + articleId +
			", writer='" + writer + '\'' +
			", title='" + title + '\'' +
			", content='" + content + '\'' +
			", writingDate=" + writingDate +
			'}';
	}
}
