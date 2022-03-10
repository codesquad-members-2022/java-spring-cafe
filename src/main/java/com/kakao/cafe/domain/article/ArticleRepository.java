package com.kakao.cafe.domain.article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);
    Optional<Article> findById(int id);
    List<Article> findAll();
    void clear();
}
