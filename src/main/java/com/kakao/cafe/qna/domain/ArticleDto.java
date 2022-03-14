package com.kakao.cafe.qna.domain;

import static com.kakao.cafe.common.utils.StringValidator.*;
import static com.kakao.cafe.common.utils.TypeConvertor.*;

import java.time.LocalDate;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;

public class ArticleDto {
	public static final String ERROR_OF_WRITER_BLANK = "작성자를 입력하세요.";
	public static final String ERROR_OF_TITLE_BLANK = "제목을 입력하세요.";
	public static final String ERROR_OR_CONTENT_BLANK = "내용을 입력하세요";
	public static final String DISTINGUISHER_COMMA = ",";

	public static class WriteRequest {
		private final String writer;
		private final String title;
		private final String contents;
		private final String userId;

		public WriteRequest(String writer, String title, String contents, String userId) {
			this.writer = writer;
			this.title = title;
			this.contents = StringEscapeUtils.escapeHtml4(contents);
			this.userId = userId;
		}

		public String getWriter() {
			return writer;
		}

		public String getTitle() {
			return title;
		}

		public String getContents() {
			return contents;
		}

		public String getUserId() {
			return userId;
		}

		public void isValid(Logger logger) {
			try {
				if (isOneMoreBlank()) {
					throw new IllegalArgumentException(getErrorMessageWithBlank());
				}
			} catch (IllegalArgumentException exception) {
				logger.error("error of the request question : {}", exception);
			}
		}

		private boolean isOneMoreBlank() {
			return isWriterBlank() || isTitleBlank() || isContentsBlank() || isUserIdBlank();
		}

		private boolean isContentsBlank() {
			return isNullOrBlank(this.contents);
		}

		private boolean isTitleBlank() {
			return isNullOrBlank(this.title);
		}

		private boolean isWriterBlank() {
			return isNullOrBlank(this.writer);
		}

		private boolean isUserIdBlank() {
			return isNullOrBlank(this.userId);
		}

		public String getErrorMessageWithBlank() {
			StringBuffer sb = new StringBuffer();
			if (isWriterBlank()) {
				sb.append(ERROR_OF_WRITER_BLANK)
					.append(DISTINGUISHER_COMMA);
			}
			if (isTitleBlank()) {
				sb.append(ERROR_OF_TITLE_BLANK)
					.append(DISTINGUISHER_COMMA);
			}
			if (isContentsBlank()) {
				sb.append(ERROR_OR_CONTENT_BLANK);
			}

			int lastIdx = sb.length() - 1;
			if (sb.lastIndexOf(DISTINGUISHER_COMMA) == lastIdx) {
				sb.deleteCharAt(lastIdx);
			}
			return sb.toString();
		}

		@Override
		public String toString() {
			return "ArticleDto{" +
				"writer='" + writer + '\'' +
				", title='" + title + '\'' +
				", contents='" + contents + '\'' +
				'}';
		}
	}

	public static class WriteResponse {
		private final String id;
		private final String writer;
		private final String title;
		private final String contents;
		private final LocalDate writingDate;

		public WriteResponse(Article article) {
			this.id = toTextFromLong(article.getId());
			this.writer = article.getWriter();
			this.title = article.getTitle();
			this.contents = StringEscapeUtils.unescapeHtml4(article.getContent());
			this.writingDate = article.getWritingDate();
		}

		public String getId() {
			return id;
		}

		public String getWriter() {
			return writer;
		}

		public String getTitle() {
			return title;
		}

		public String getContents() {
			return contents;
		}

		public LocalDate getWritingDate() {
			return writingDate;
		}

		@Override
		public String toString() {
			return "WriteResponse{" +
				"id='" + id + '\'' +
				", writer='" + writer + '\'' +
				", title='" + title + '\'' +
				", contents='" + contents + '\'' +
				", writingDate=" + writingDate +
				'}';
		}
	}
}
