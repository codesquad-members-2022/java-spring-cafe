package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

@Repository
public class VolatilityArticleRepository implements DomainRepository<Article, Integer> {

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
    public Optional<Article> findOne(Integer index) {
        return Optional.ofNullable(articles.get(index - 1));
    }
}
