package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.NewArticleParam;
import com.kakao.cafe.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolatilityArticleService implements ArticleService {

    private final Repository<Article, Long> articleRepository;

    public VolatilityArticleService(Repository<Article, Long> articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> searchAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article add(NewArticleParam newArticleParam) {
        return articleRepository.save(newArticleParam.convertToArticle())
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    public Article search(long id) {
        return articleRepository.findOne(id)
                .orElseThrow(() -> new RuntimeException());
    }
}
