package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.ArticleWriteRequest;
import com.kakao.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article write(ArticleWriteRequest articleRequest) {
        Article article = new Article(articleRequest);
        return articleRepository.save(article);
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }
}
