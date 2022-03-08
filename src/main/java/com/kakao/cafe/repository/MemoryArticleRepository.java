package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryArticleRepository implements ArticleRepository{
    private static List<Article> articles = new ArrayList<>();
    private static int sequence = 0;

    @Override
    public Article save(Article article) {
        article.setId(++sequence);
        articles.add(article);
        return article;
    }

    @Override
    public Optional<Article> findById(int id) {
        return articles.stream()
                .filter(article -> article.getId() == id)
                .findAny();
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }

    @Override
    public void deleteById(int id) {
        articles.removeIf(article -> article.getId() == id);
    }
}
