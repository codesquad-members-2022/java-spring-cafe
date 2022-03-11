package com.kakao.cafe.repository.article;

import java.util.List;
import java.util.Optional;

import com.kakao.cafe.domain.article.Article;

public interface ArticleRepository {
    List<Article> findAll();

    Optional<Article> findById(Long id);

    void save(Article article);

    void deleteAll();
}
