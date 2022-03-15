package com.kakao.cafe.web.service.article;

import com.kakao.cafe.core.domain.article.Article;
import com.kakao.cafe.core.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void write(Article article) {
        articleRepository.save(article);
    }

    public Article findById(Integer id) {
        return articleRepository.findById(id).orElseThrow();
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }
}
