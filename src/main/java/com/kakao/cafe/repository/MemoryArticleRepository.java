package com.kakao.cafe.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import com.kakao.cafe.domain.Article;

public class MemoryArticleRepository implements ArticleRepository {
    private final List<Article> articles = new CopyOnWriteArrayList<>();
    private final AtomicInteger sequence = new AtomicInteger();

    @Override
    public int save(Article article) {
        sequence.compareAndSet(articles.size(), articles.size() + 1);
        int id = sequence.get();
        article.setId(id);
        article.setDate(LocalDate.now());
        articles.add(article);
        return id;
    }

    @Override
    public Optional<Article> findById(int id) {
        return Optional.ofNullable(articles.get(id - 1));
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }
}
