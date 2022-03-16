package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class VolatilityArticleRepository implements CrudRepository<Article, Integer> {

    private final List<Article> articles = new CopyOnWriteArrayList<>();

    @Override
    public List<Article> findAll() {
        return Collections.unmodifiableList(articles);
    }

    @Override
    public synchronized Optional<Article> save(Article article) {
        article.setId(articles.size() + 1);
        articles.add(article);
        return Optional.ofNullable(article);
    }

    @Override
    public Optional<Article> findById(Integer index) {
        return Optional.ofNullable(articles.get(index - 1));
    }

    @Override
    public int deleteById(Integer index) {
        return articles.remove(index - 1).getId();
    }
}
