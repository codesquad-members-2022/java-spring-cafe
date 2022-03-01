package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleCollectionRepository implements ArticleRepository {

    private List<Article> articles = new ArrayList<>();

    @Override
    public Article save(Article article) {
        articles.add(article);
        return article;
    }

    @Override
    public List<Article> findAll() {
        return articles;
    }

    @Override
    public Optional<Article> findById(int articleId) {
        return Optional.empty();
    }

    @Override
    public void deleteAll() {
        articles = new ArrayList<>();
    }
}
