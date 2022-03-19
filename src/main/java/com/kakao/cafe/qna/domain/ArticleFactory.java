package com.kakao.cafe.qna.domain;

import java.time.LocalDate;

public class ArticleFactory {
	public static Article create(String writer, String title, String content, Long cafeUserId) {
		return Article.createOf(writer, title, content, cafeUserId);
	}

	public static Article create(Long articleId,
								String writer,
								String title,
								String content,
								Long cafeUserId,
								LocalDate writingDate,
								boolean deleted){
		return Article.loadOf(articleId, writer, title, content, cafeUserId, writingDate, deleted);
	}
}
