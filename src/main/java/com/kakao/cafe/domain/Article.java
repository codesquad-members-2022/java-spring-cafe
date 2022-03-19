package com.kakao.cafe.domain;

import com.kakao.cafe.controller.ArticleForm;

public class Article {

    private final String writer;
    private final String title;
    private final String contents;
    private final int articleId;

    public Article(ArticleForm articleForm, int articleId) {
        this.writer = articleForm.getWriter();
        this.title = articleForm.getTitle();
        this.contents = articleForm.getContents();
        this.articleId = articleId;
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

    public boolean isCorrectId(int id) {
        if (articleId == id) {
            return true;
        }
        return false;
    }
}
