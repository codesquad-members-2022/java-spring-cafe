package com.kakao.cafe.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.repository.article.ArticleRepository;

@Service
public class ArticleService {

    private static final String NOT_FOUNDED_ARTICLE_ID = "[ERROR] 존재하지 않는 Article 입니다.";

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void save(Article article) {
        article.writeWhenCreated(LocalDateTime.now());
        articleRepository.save(article);
    }

    public List<Article> showAllArticles() {
        return articleRepository.findAll();
    }

    public Article showArticle(long index) {
        return articleRepository.findById(index)
            .orElseThrow(IllegalArgumentException::new);
    }

    public void clear() {
        articleRepository.deleteAll();
    }

    public void update(Long index, Article updateArticle) {
        articleRepository.findById(index)
            .orElseThrow(() -> new IllegalArgumentException(NOT_FOUNDED_ARTICLE_ID));
        updateArticle.setId(index);
        articleRepository.save(updateArticle);
    }
}
