package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article save(Article article);

    Optional<Article> findByIndex(int index);

    List<Article> findAll();

    void clearStore();
}
