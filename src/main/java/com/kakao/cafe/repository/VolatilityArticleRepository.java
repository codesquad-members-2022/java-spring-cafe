package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class VolatilityArticleRepository implements DomainRepository<Article, Integer> {

    private final List<Article> articles = Collections.synchronizedList(new ArrayList<>());

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
