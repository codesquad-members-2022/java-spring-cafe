package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import java.util.Date;
import java.util.List;

public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article upload(Article userArticle) {
        userArticle.setCreatedDate(new Date());
        return articleRepository.save(userArticle);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findOne(Integer id) {
        return articleRepository.findById(id);
    }

    public void deleteAll() {
        articleRepository.clear();
    }
}
