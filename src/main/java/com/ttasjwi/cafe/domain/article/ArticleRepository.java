package com.ttasjwi.cafe.domain.article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Long save(Article article);
    Optional<Article> findByArticleId(Long articleId);
    List<Article> findAll();
}
