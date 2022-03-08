package com.kakao.cafe.core.domain.article;

import java.time.LocalDateTime;

public class Article {

    private int id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
    private int viewCount;
}
