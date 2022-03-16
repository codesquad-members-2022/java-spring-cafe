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
	private boolean deleted;

	public Article() {
	}

	public static Article createOf(String writer, String title, String content, Long cafeUserId) {
		Article article = new Article();
		article.writer = writer;
		article.title = title;
		article.content = content;
		article.cafeUserId = cafeUserId;
		article.writingDate = LocalDate.now();
		article.deleted = false;
		return article;
	}

	public static Article loadOf(Long articleId,
								String writer,
								String title,
								String content,
								Long cafeUserId,
								LocalDate writingDate,
								boolean deleted) {
		Article article = new Article();
		article.articleId = articleId;
		article.writer = writer;
		article.title = title;
		article.content = content;
		article.cafeUserId = cafeUserId;
		article.writingDate = writingDate;
		article.deleted = deleted;
		return article;
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

	public boolean isDeleted() {
		return deleted;
	}
}
