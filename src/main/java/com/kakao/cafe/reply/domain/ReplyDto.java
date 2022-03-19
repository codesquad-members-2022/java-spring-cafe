package com.kakao.cafe.reply.domain;

import static com.kakao.cafe.common.utils.StringValidator.*;
import static com.kakao.cafe.common.utils.TypeConvertor.*;
import static com.kakao.cafe.user.domain.UserDto.*;

import java.time.LocalDate;

import org.apache.commons.text.StringEscapeUtils;

public class ReplyDto {
	public static class Response {
		private final String replyId;
		private final String replier;
		private final String replyContent;
		private final LocalDate replyWritingDate;
		private final String userId;

		public Response(Reply reply) {
			this.replyId = toTextFromLong(reply.getReplyId());
			this.replier = reply.getReplier();
			this.replyContent = StringEscapeUtils.unescapeHtml4(reply.getContent());
			this.replyWritingDate = reply.getWritingDate();
			this.userId = reply.getCafeUsersId();
		}
		public String getReplyId() {
			return replyId;
		}

		public String getReplier() {
			return replier;
		}

		public String getReplyContent() {
			return replyContent;
		}

		public LocalDate getReplyWritingDate() {
			return replyWritingDate;
		}

		public String getUserId() {
			return userId;
		}
	}
}
