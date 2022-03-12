package com.kakao.cafe.core.repository.article;

import com.kakao.cafe.core.domain.article.Article;
import com.kakao.cafe.web.service.member.EntityManager;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ArticleRepository {

    private final EntityManager<Article> entityManager;

    public ArticleRepository(EntityManager<Article> entityManager) {
        this.entityManager = entityManager;
    }

    public Article save(Article article) {
        return entityManager.persist(article);
    }

    public List<Article> findAll() {
        return entityManager.getArticles();
    }

    public Optional<Article> findById(Integer id) {
        return entityManager.getArticles()
                .stream()
                .filter(article -> Objects.equals(article.getId(), id))
                .findAny();
    }
}
