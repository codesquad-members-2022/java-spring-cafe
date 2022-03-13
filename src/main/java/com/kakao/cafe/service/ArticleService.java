package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.NewArticleParam;
import com.kakao.cafe.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final Repository<Article, Long> articleRepository;

    public ArticleService(Repository<Article, Long> articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> searchAll() {
        return articleRepository.findAll();
    }

    public Article add(NewArticleParam newArticleParam) {
        return articleRepository.save(newArticleParam.convertToArticle())
                .orElseThrow(() -> new RuntimeException());
    }

    public Article search(long id) {
        return articleRepository.findOne(id)
                .orElseThrow(() -> new RuntimeException());
    }
}
