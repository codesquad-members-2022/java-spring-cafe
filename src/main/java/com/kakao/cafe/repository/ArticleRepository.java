package com.kakao.cafe.repository;

import com.kakao.cafe.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    List<Article> findAllArticle();

    Optional<Article> findIdArticle(int articleId);
}
