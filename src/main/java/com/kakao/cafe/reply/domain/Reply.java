package com.kakao.cafe.reply.domain;

import java.time.LocalDate;

public class Reply {
	private Long replyId;
	private final String replier;
	private final String content;
	private final LocalDate writingDate;
	private final Long articleId;
	private final String cafeUsersId;
	private final Long cafeId;
	private final boolean deleted;

	/*
		User, Article 은 정적팩토리메서드 패턴 이용해서 사용처별로 생성자를 구분지어 사용하게 한 후에
		Factory 클래스를 두어 OCP 가능하게 하고자 했습니다.

		Reply는 final로 필수 입력값들을 정의해 놓고, Factory에서 생성자를 구분하여 입력되도록 해보았으나 OCP와는 멀어진거 같아요.

		어떨때 어떻게 사용되도록, 또는 생성자를 이용해야 하는지 고민입니다.
	 */
	public Reply(String replier,
				String content,
				Long articleId,
				String cafeUsersId,
				Long cafeId) {
		this.replier = replier;
		this.content = content;
		this.articleId = articleId;
		this.cafeUsersId = cafeUsersId;
		this.writingDate = LocalDate.now();
		this.cafeId = cafeId;
		this.deleted = false;
	}

	public Reply(Long replyId,
				String replier,
				String content,
				LocalDate writingDate,
				Long articleId,
				String cafeUsersId,
				Long cafeId,
				boolean deleted) {
		this.replyId = replyId;
		this.replier = replier;
		this.content = content;
		this.writingDate = writingDate;
		this.articleId = articleId;
		this.cafeUsersId = cafeUsersId;
		this.cafeId = cafeId;
		this.deleted = deleted;
	}

	public Long getReplyId() {
		return replyId;
	}

	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}

	public String getReplier() {
		return replier;
	}

	public String getContent() {
		return content;
	}

	public LocalDate getWritingDate() {
		return writingDate;
	}

	public Long getArticleId() {
		return articleId;
	}

	public String getCafeUsersId() {
		return cafeUsersId;
	}

	public Long getCafeId() {
		return cafeId;
	}

	public boolean isDeleted() {
		return deleted;
	}
}
