package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {
    Article save(Article article);

    List<Article> findAll();
}
