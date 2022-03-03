package com.kakao.cafe.qna.domain;

import java.time.LocalDate;
import java.util.Objects;

import org.slf4j.Logger;

public class ArticleDto {
	public static final String ERROR_OF_WRITER_BLANK = "작성자를 입력하세요.";
	public static final String ERROR_OF_TITLE_BLANK = "제목을 입력하세요.";
	public static final String ERROR_OR_CONTENT_BLANK = "내용을 입력하세요";
	public static final String DISTINGUISHER_COMMA = ",";

	public static class WriteRequest {
		private String writer;
		private String title;
		private String contents;

		public String getWriter() {
			return writer;
		}

		public void setWriter(String writer) {
			this.writer = writer;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getContents() {
			return contents;
		}

		public void setContents(String contents) {
			this.contents = contents;
		}

		public void isValid(Logger logger) {
			if (isOneMoreBlank()) {
				logger.warn("request question : {}", this);
				throw new IllegalArgumentException(getErrorMessageWithBlank());
			}
		}

		private boolean isOneMoreBlank() {
			return isWriterBlank() || isTitleBlank() || isContentsBlank();
		}

		private boolean isContentsBlank() {
			return Objects.isNull(this.contents) || this.contents.isBlank();
		}

		private boolean isTitleBlank() {
			return Objects.isNull(this.title) || this.title.isBlank();
		}

		private boolean isWriterBlank() {
			return Objects.isNull(this.writer) || this.writer.isBlank();
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
		private String id;
		private String writer;
		private String title;
		private String contents;
		private LocalDate writingDate;

		public WriteResponse(Article article) {
			this.id = String.valueOf(article.getId());
			this.writer = article.getWriter();
			this.title = article.getTitle();
			this.contents = article.getContent();
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
