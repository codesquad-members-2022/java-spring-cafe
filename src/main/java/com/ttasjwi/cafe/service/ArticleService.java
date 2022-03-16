package com.ttasjwi.cafe.service;

import com.ttasjwi.cafe.controller.request.ArticleWriteRequest;
import com.ttasjwi.cafe.domain.article.Article;
import com.ttasjwi.cafe.domain.article.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Long saveArticle(ArticleWriteRequest articleWriteRequest) {
        Article article = articleWriteRequest.toEntity();
        articleRepository.save(article);
        return article.getArticleId();
    }

    public Article findOne(Long articleId) {
        return articleRepository.findByArticleId(articleId)
                .orElseThrow(()-> new NoSuchElementException("유효하지 않은 게시글 번호입니다."));
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }
}
