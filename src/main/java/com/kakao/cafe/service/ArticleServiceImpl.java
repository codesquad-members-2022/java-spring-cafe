package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;

import java.util.List;
import java.util.Optional;

public class ArticleServiceImpl implements ArticleService{
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Long post(Article article) {
        articleRepository.save(article);
        return article.getId();
    }

    @Override
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public void update(Article article) {

    }

    @Override
    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Optional<Article> findOneArticle(String title) {
        return articleRepository.findByTitle(title);
    }
}
