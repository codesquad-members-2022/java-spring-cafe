package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    List<Article> findAll();

    Optional<Article> findById(int articleId);

    void deleteAll();

}
