package com.kakao.cafe.web.dto.question;

import com.kakao.cafe.domain.Question;

public class QuestionDto {
    private String writer;
    private String title;
    private String contents;

    public QuestionDto(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

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

    public static QuestionDto from(Question question) {
        return new QuestionDto(question.getWriter(), question.getTitle(), question.getContents());
    }

    public Question toEntity() {
        return Question.builder()
                .writer(this.writer)
                .title(this.title)
                .contents(this.contents)
                .build();
    }
}
