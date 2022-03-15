package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class MemoryArticleRepository implements ArticleRepository {

    private final List<Article> store = new CopyOnWriteArrayList<>();

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
        return List.copyOf(store);
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
