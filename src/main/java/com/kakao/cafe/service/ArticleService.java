package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleService {

    private final String NO_MATCH_ARTICLE_MESSAGE = "일치하는 질문이 없습니다.";
    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Long nextSequence() {
        return articleRepository.nextSequence();
    }

    public Long save(Article article) {
        Long id = articleRepository.save(article);

        return id;
    }

    public Article findById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NO_MATCH_ARTICLE_MESSAGE));
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public void deleteAllArticles() {
        articleRepository.deleteAllArticles();
    }
}
