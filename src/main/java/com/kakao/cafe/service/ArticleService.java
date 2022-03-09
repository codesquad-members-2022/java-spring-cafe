package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article create(Article article) {
        return articleRepository.save(article);
    }

    public Optional<Article> findById(int id) {
        return articleRepository.findByid(id);
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
}
