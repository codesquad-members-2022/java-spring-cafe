package com.kakao.cafe.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.Article;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private List<Article> articles = new ArrayList<>();

    @Override
    public int save(Article article) {
        int id = articles.size() + 1;
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
