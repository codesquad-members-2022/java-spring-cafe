package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

import com.kakao.cafe.domain.Article;

public interface ArticleRepository {

    void save(Article article);

    Optional<Article> findById(Long index);

    List<Article> findAll();
}
