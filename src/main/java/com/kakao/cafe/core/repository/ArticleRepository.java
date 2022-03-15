package com.kakao.cafe.core.repository;

import com.kakao.cafe.config.study.RepositoryDefinition;
import com.kakao.cafe.core.domain.article.Article;
import com.kakao.cafe.core.repository.member.CrudRepository;

import java.util.List;

@RepositoryDefinition(domainClass = Article.class, idClass = Integer.class)
public interface ArticleRepository extends CrudRepository<Article, Integer> {
    List<Article> findAll();
}
