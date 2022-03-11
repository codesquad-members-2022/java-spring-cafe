package com.kakao.cafe.repository.article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.repository.article.ArticleRepository;

@Component
public class MemoryArticleRepository implements ArticleRepository {

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

    @Override
    public void deleteAll() {
        articles.clear();
    }
}
