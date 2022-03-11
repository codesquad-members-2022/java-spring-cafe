package com.kakao.cafe.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.repository.article.MemoryArticleRepository;

@Service
public class ArticleService {

    private final MemoryArticleRepository articleRepository;

    public ArticleService(MemoryArticleRepository articleRepository) {
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
}
