package com.kakao.cafe.web.controller.article.dto;

import com.kakao.cafe.core.domain.article.Article;
import com.kakao.cafe.core.repository.SequenceGenerator;

import java.time.LocalDateTime;

import static com.kakao.cafe.config.DataGenerator.getRandomLocalDateTime;

public class PostWriteRequest {

    private String title;
    private String content;
    private String writer;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
    private int viewCount;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public Article toEntity() {
        LocalDateTime createAt = getRandomLocalDateTime();
        return new Article.Builder()
                .id(SequenceGenerator.getArticleSequence())
                .title(title)
                .content(content)
                .writer(writer)
                .createAt(createAt)
                .lastModifiedAt(null)
                .build();
    }
}
