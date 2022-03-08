package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryArticleRepository implements ArticleRepository{
    private static List<Article> articles = new ArrayList<>();
    private static Long sequence = 0L;

    @Override
    public Article save(Article article) {
        article.setId(++sequence);
        articles.add(article);
        return article;
    }

    @Override
    public Optional<Article> findByTitle(String title) {
        return articles.stream()
                .filter(article -> article.getTitle().equals(title))
                .findAny();
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }

    @Override
    public void deleteById(Long id) {
        articles.removeIf(article -> article.getId().equals(id));
    }
}
