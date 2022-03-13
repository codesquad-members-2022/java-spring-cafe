package com.kakao.cafe.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.Article;

@Repository
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
    public Optional<Article> findById(int index) {
        return Optional.ofNullable(articles.get(index - 1));
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }
}
