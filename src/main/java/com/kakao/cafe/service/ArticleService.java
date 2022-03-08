package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Long post(Article article);

    void deleteById(Long id);

    void update(Article article);

    List<Article> findArticles();

    Optional<Article> findOneArticle(String title);
}
