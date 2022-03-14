package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import java.util.List;

public interface ArticleRepository {

    Article saveArticle(Article article);
    List<Article> loadAllArticles();
}
