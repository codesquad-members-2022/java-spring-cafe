package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryArticleRepository implements ArticleRepository {

    private final List<Article> store = new ArrayList<>();

    @Override
    public Article save(Article article) {
        store.add(article);
        return article;
    }

    @Override
    public Optional<Article> findById(int id) {
        return store.stream()
                .filter(article -> article.isCorrectId(id))
                .findAny();
    }

    @Override
    public List<Article> findAll() {
        return store;
    }

    @Override
    public void deleteAll() {
        store.clear();
    }

    @Override
    public int size() {
        return store.size();
    }
}
