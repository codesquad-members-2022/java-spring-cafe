package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolatilityArticleService implements ArticleService {

    private final Repository<Article, Integer> articleRepository;

    public VolatilityArticleService(Repository<Article, Integer> articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> searchAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article add(Article article) {
        return articleRepository.save(article)
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    public Article search(int id) {
        return articleRepository.findOne(id)
                .orElseThrow(() -> new RuntimeException());
    }
}
