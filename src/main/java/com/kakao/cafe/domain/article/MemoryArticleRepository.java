package com.kakao.cafe.domain.article;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryArticleRepository implements ArticleRepository {

    private static final int STORAGE_KEY = 1;

    private final List<Article> articles = new ArrayList<>();

    @Override
    public void save(Article article) {
        article.setId(generateId());
        articles.add(article);
    }

    @Override
    public Optional<Article> findById(int id) {
        int index = id - STORAGE_KEY;
        if(checkIndex(index)) {
            return Optional.ofNullable(articles.get(index));
        }
        return Optional.empty();
    }

    @Override
    public List<Article> findAll() {
        return articles;
    }

    @Override
    public void clear() {
        articles.clear();
    }

    private int generateId() {
        return articles.size() + STORAGE_KEY;
    }

    private boolean checkIndex(int index) {
        return index < articles.size() && index >= 0;
    }
}
