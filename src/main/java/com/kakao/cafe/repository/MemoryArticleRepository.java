package com.kakao.cafe.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.kakao.cafe.domain.Article;

public class MemoryArticleRepository implements ArticleRepository {
    private List<Article> articles = new ArrayList<>();

    @Override
    public void save(Article article) {
        article.setId(articles.size() + 1L);
        article.setDate(LocalDate.now().toString());
        articles.add(article);
    }

    @Override
    public Optional<Article> findById(Long index) {
        return Optional.ofNullable(articles.get(index.intValue() - 1));
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }
}
