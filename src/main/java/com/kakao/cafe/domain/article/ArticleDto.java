package com.kakao.cafe.domain.article;

public class ArticleDto {

    private Long id;
    private final String writer;
    private final String title;
    private final String contents;

    public ArticleDto(String writer, String title, String contents) {
        this(null, writer, title, contents);
    }

    public ArticleDto(Long id, String writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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

    public void isEmpty() {
        if (title.isEmpty() || contents.isEmpty()) {
            throw new IllegalArgumentException("제목 혹은 컨텐츠가 비어있습니다.");
        }
    }

    public Article convertToArticle() {
        return new Article(writer, title, contents);
    }
}
