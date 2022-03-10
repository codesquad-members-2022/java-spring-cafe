package com.kakao.cafe.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.kakao.cafe.domain.article.Article;

@Component
public class ArticleRepository implements CustomRepository<Article> {

    private final List<Article> articles = Collections.synchronizedList(new ArrayList<>());

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }

    @Override
    public void save(Article article) {
        article.setId((long)articles.size() + 1);
        articles.add(article);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articles.stream()
            .filter(article -> article.getId().equals(id))
            .findAny();
    }

    public Optional<Article> findByIndex(int index) {
        return articles.stream()
            .filter(article -> article.getId() == index)
            .findFirst();
    }
}
