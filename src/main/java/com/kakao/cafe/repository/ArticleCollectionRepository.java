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
        article.setArticleId(articles.size() + 1);
        articles.add(article);
        return article;
    }

    @Override
    public List<Article> findAll() {
        return articles;
    }

    @Override
    public Optional<Article> findById(Integer articleId) {
        return articles.stream()
            .filter(article -> article.getArticleId().equals(articleId))
            .findAny();
    }

    @Override
    public void deleteAll() {
        articles = new ArrayList<>();
    }
}
