package com.kakao.cafe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.repository.ArticleRepository;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public List<Article> showAllArticles() {
        return articleRepository.findAll();
    }

    public Article showArticle(int index) {
        return articleRepository.findByIndex(index)
            .orElseThrow(IllegalArgumentException::new);
    }
}
