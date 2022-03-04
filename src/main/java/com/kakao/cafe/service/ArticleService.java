package com.kakao.cafe.service;

import com.kakao.cafe.dto.UserArticle;
import com.kakao.cafe.repository.ArticleRepository;
import java.util.Date;
import java.util.List;

public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public UserArticle upload(UserArticle userArticle) {
        userArticle.setCreatedDate(new Date());
        return articleRepository.save(userArticle);
    }

    public List<UserArticle> findAllArticles() {
        return articleRepository.findAll();
    }

    public UserArticle findOneArticle(Integer id) {
        return articleRepository.findById(id);
    }
}
