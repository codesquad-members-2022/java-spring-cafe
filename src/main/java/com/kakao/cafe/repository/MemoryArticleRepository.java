package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MemoryArticleRepository implements ArticleRepository{
    private List<Article> articles = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Article save(Article article) {
        for (int i = 0; i < articles.size(); i++) {
            if (articles.get(i) == null) {
                articles.add(i, article);
                return article;
            }
        }
        articles.add(article);
        return article;
    }

    @Override
    public Optional<Article> findByIndex(int index) {
        return articles.stream()
                .filter(article -> article.compareBy(index))
                .findAny();
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }

    public int size() {
        return articles.size();
    }

    public void clearArticles() {
        articles.clear();
    }
}
