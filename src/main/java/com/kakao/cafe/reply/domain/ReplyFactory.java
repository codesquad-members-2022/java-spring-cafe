package com.kakao.cafe.reply.domain;

import java.time.LocalDate;

public class ReplyFactory {
	public static Reply createOf(String replier,
								String content,
								Long articleId,
								String cafeUserId,
								Long cafeId) {
		return new Reply(replier, content, articleId, cafeUserId, cafeId);
	}

	public static Reply loadOf(Long replyId,
								String replier,
								String content,
								LocalDate writingDate,
								Long articleId,
								String cafeUsersId,
								Long cafeId,
								boolean deleted) {
		return new Reply(replyId, replier, content, writingDate, articleId, cafeUsersId, cafeId, deleted);
	}
}
