package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Long save(Article article);

    Optional<Article> findById(Long id);

    List<Article> findAll();

    Long delete(Long id);

    void update(Long id, Article article);
}
