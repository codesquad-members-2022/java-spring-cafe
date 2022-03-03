package com.kakao.cafe.repository;

import com.kakao.cafe.entity.Article;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleMemorySaveRepository implements ArticleRepository {

    private final List<Article> articleStore = new ArrayList<>();

    @Override
    public Article save(Article article) {
        articleStore.add(article);
        return article;
    }

    @Override
    public List<Article> findAllArticle() {
        return articleStore;
    }

    @Override
    public Optional<Article> findIdArticle(int articleId) {
        return Optional.of(articleStore.get(articleId));
    }
}
