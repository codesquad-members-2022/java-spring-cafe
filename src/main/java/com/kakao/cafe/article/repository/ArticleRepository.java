package com.kakao.cafe.article.repository;

import com.kakao.cafe.article.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Optional<Article> save(Article article);

    Optional<Article> findById(Long savedId);

    Optional<List<Article>> findAll();

    void deleteAll();
}
