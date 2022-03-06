package com.kakao.cafe.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.kakao.cafe.domain.article.Article;

@Component
public class ArticleRepository implements CustomRepository<Article> {

    private final List<Article> articles;

    public ArticleRepository(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }

    @Override
    public void save(Article article) {
        article.setId(articles.size() + 1);
        articles.add(article);
    }
}
