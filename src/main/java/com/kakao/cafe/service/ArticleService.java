package com.kakao.cafe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.exception.ErrorMessage;
import com.kakao.cafe.repository.ArticleRepository;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void add(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    public Article findById(int index) {
        return articleRepository.findById(index).orElseThrow(() ->
            new IllegalArgumentException(ErrorMessage.NO_MATCH_ARTICLE.get()));
    }
}
