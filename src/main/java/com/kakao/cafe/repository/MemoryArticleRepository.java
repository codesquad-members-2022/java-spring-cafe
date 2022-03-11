package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MemoryArticleRepository implements ArticleRepository{
    private List<Article> articleStore = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Article save(Article article) {
        for (int i = 0; i < articleStore.size(); i++) {
            if (articleStore.get(i) == null) {
                return store(article, i);
            }
        }
        return store(article, articleStore.size());
    }

    private Article store(Article article, int index) {
        article.setIndex(index);
        articleStore.add(article);
        return article;
    }

    @Override
    public Optional<Article> findByIndex(int index) throws IndexOutOfBoundsException{
        return Optional.ofNullable(articleStore.get(index));
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articleStore);
    }

    @Override
    public void clearStore() {
        articleStore.clear();
    }

    public int size() {
        return articleStore.size();
    }
}
