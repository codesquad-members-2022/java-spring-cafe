package com.kakao.cafe.web.domain.article;

public class Article {

	private Long id;
	private final String writer;
	private String title;
	private String contents;

	public Article(String writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}

	public Long getId() {
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

}
