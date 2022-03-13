package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

@org.springframework.stereotype.Repository
public class VolatilityArticleRepository implements Repository<Article, Long> {

    private final Vector<Article> articles = new Vector<>();

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
    public Optional<Article> findOne(Long index) {
        return Optional.ofNullable(articles.get(index.intValue() - 1));
    }
}
