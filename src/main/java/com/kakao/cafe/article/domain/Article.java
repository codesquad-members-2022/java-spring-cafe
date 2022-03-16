package com.kakao.cafe.article.domain;

import com.kakao.cafe.article.controller.dto.ArticleWriteRequest;
import com.kakao.cafe.exception.domain.InvalidFieldLengthException;
import com.kakao.cafe.exception.domain.RequiredFieldNotFoundException;
import com.kakao.cafe.users.domain.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article {

    private Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private long viewCount;

    public Article(Long id, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate, long viewCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.viewCount = viewCount;

        validateRequiredField(this);
        validateFieldLength(this);
    }

    public Article(String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this(null, title, content, createdDate, modifiedDate, 0);
    }

    public static Article createWithWriteRequest(ArticleWriteRequest writeRequest) {
        LocalDateTime now = LocalDateTime.now();

        return new Article(
                writeRequest.getTitle(),
                writeRequest.getContent(),
                now,
                now
        );
    }

    // ---- public method ----
    public boolean equalsId(Long id) {
        return this.id.intValue() == id.intValue();
    }

    public boolean equalsTitle(String title) {
        return this.title.equals(title);
    }

    public boolean equalsContent(String content) {
        return this.content.equals(content);
    }

    public boolean equalsViewCount(long viewCount) {
        return this.viewCount == viewCount;
    }

    public void addViewCount() {
        this.viewCount++;
    }

    public void updateModifiedDate() {
        this.modifiedDate = LocalDateTime.now();
    }

    // ---- private method ----
    private void validateRequiredField(Article article) {
        if (article.getTitle() == null) {
            throw new RequiredFieldNotFoundException();
        }
    }

    private void validateFieldLength(Article article) {
        if (article.getTitle().length() > 100 ) {
            throw new InvalidFieldLengthException();
        }
    }

    // ---- getter setter ----
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public long getViewCount() {
        return viewCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return Objects.equals(getId(), article.getId()) &&
                Objects.equals(title, article.title) &&
                Objects.equals(content, article.content) &&
                Objects.equals(createdDate, article.createdDate) &&
                Objects.equals(modifiedDate, article.modifiedDate);
    }
}
