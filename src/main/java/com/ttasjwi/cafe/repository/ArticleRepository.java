package com.ttasjwi.cafe.repository;

import com.ttasjwi.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Long save(Article article);
    Optional<Article> findByArticleId(Long articleId);
    List<Article> findAll();
}
