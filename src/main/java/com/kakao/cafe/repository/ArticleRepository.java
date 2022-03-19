package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

public interface ArticleRepository {
    Article save(Article article);
    void delete();
}
