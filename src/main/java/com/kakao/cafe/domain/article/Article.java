package com.kakao.cafe.domain.article;

public class Article {

    private Long id;
    private final String writer;
    private final String title;
    private final String contents;


    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ArticleDto convertToDto() {
        ArticleDto articleDto = new ArticleDto(writer, title, contents);
        articleDto.setId(id);
        return articleDto;
    }
}
