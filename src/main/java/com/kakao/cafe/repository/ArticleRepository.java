package com.kakao.cafe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.exception.DuplicatedIdException;

@Component
public class ArticleRepository implements CustomRepository<Article> {

    private final List<Article> articles;

    public ArticleRepository(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public Optional<Article> findByUserId(String id) {
        return Optional.empty();
    }

    @Override
    public List<Article> findAll() {
        return null;
    }

    @Override
    public void save(Article article) {
        articles.add(article);
    }
}
