package com.kakao.cafe.domain.article;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static final int STORAGE_KEY = 1;

    private final Map<Integer, Article> articles = new HashMap<>();

    @Override
    public Article save(Article article) {
        article.setId(generateId());
        articles.put(article.getId(), article);
        return article;
    }

    @Override
    public Optional<Article> findById(int id) {
        return Optional.ofNullable(articles.get(id));
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articles.values());
    }

    @Override
    public void clear() {
        articles.clear();
    }

    private int generateId() {
        return articles.size() + STORAGE_KEY;
    }

}
