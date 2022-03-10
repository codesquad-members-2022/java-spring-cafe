package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article upload(Article userArticle) {
        return articleRepository.save(userArticle);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findOne(int id) {
        return articleRepository.findById(id);
    }

    public void deleteAll() {
        articleRepository.clear();
    }
}
