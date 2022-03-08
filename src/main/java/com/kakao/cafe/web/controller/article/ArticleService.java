package com.kakao.cafe.web.controller.article;

import com.kakao.cafe.core.domain.article.Article;
import com.kakao.cafe.core.repository.article.ArticleRepository;

import java.util.List;

public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findById(int id) {
        return articleRepository.findById(id).orElseThrow();
    }
}
