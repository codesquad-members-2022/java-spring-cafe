package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {

    void save(Article article);

    List<Article> findAll();

    Article findById(int id);
}
