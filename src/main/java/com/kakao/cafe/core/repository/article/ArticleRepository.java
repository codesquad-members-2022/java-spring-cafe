package com.kakao.cafe.core.repository.article;

import com.kakao.cafe.core.domain.article.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticleRepository {

    private final List<Article> articles;

    public ArticleRepository() {
        this.articles = new ArrayList<>();
    }

    public Article insert(Article article) {
        articles.add(article);
        return article;
    }

    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }

    public Optional<Article> findById(int id) {
        return articles.stream()
                .filter(article->article.getId()==id)
                .findAny();
    }
}
