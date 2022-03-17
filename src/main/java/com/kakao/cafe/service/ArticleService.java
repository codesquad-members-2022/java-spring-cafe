package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void saveArticle(Article article) {
        articleRepository.saveArticle(article);
    }

    public List<Article> loadAllArticles() {
        return articleRepository.loadAllArticles();
    }

    public Article loadOneArticle(Long index) {
        return articleRepository.loadOneArticle(index).orElseThrow(()-> new NoSuchElementException("해당하는 글이 존재하지 않습니다"));
    }
}
