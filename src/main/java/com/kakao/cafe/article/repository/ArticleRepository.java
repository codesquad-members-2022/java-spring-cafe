package com.kakao.cafe.article.repository;

import com.kakao.cafe.article.domain.Article;

import java.util.Optional;

public interface ArticleRepository {

    Optional<Article> save(Article article);

    Optional<Article> findById(Integer savedId);

    void deleteAll();
}
