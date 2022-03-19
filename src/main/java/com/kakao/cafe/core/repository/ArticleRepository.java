package com.kakao.cafe.core.repository;

import com.kakao.cafe.config.study.RepositoryDefinition;
import com.kakao.cafe.core.domain.article.Article;

import java.util.List;

@RepositoryDefinition(domainClass = Article.class, idClass = Integer.class)
public interface ArticleRepository extends CrudRepository<Article, Integer> {
    List<Article> findAll();
}
