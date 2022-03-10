package com.kakao.cafe.service;

import com.kakao.cafe.controller.ArticleDto;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.ArticleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void add(ArticleDto articleDto) {
        articleRepository.save(new Article(articleDto));
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

}
