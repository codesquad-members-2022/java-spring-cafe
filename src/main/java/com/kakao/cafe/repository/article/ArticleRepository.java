package com.kakao.cafe.repository.article;

import com.kakao.cafe.domain.article.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    Optional<Article> findById(Long id);

    List<Article> findAll();

    void clear();
}
