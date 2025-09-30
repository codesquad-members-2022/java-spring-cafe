package com.kakao.cafe.domain;

public class Question {
    private Long id;
    private String writer;
    private String title;
    private String contents;

    public Question() {}

    public Question(String writer, String title, String contents) {
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

    public static QuestionBuilder builder() {
        return new QuestionBuilder();
    }

    public static class QuestionBuilder {

        private Long id;
        private String writer;
        private String title;
        private String contents;

        public QuestionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public QuestionBuilder writer(String writer) {
            this.writer = writer;
            return this;
        }

        public QuestionBuilder title(String title) {
            this.title = title;
            return this;
        }

        public QuestionBuilder contents(String contents) {
            this.contents = contents;
            return this;
        }

        public Question build() {
            Question question = new Question();
            question.id = this.id;
            question.writer = this.writer;
            question.title = this.title;
            question.contents = this.contents;
            return question;
        }
    }
}
