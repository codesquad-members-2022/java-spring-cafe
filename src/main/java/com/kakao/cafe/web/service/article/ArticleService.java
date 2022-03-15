package com.kakao.cafe.web.service.article;

import com.kakao.cafe.core.domain.article.Article;
import com.kakao.cafe.core.repository.article.ArticleRepository;

import java.util.List;

public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void write(Article article) {
        articleRepository.save(article);
    }

    public Article findById(int id) {
        return articleRepository.findById(id).orElseThrow();
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }
}
