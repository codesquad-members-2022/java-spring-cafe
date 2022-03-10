package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    void save(Article article);
    void deleteAll();
    List<Article> findAll();
    Optional<Article> findById(int id);
}
