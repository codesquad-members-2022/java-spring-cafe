package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

import com.kakao.cafe.domain.Article;

public interface ArticleRepository {

    void save(Article article);

    Optional<Article> findById(int index);

    List<Article> findAll();
}
