package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    int save(Article article);

    Optional<Article> findById(int id);

    List<Article> findAll();
}
