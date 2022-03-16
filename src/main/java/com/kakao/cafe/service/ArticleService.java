package com.kakao.cafe.service;

import com.kakao.cafe.controller.article.ArticleForm;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.article.JdbcArticleRepository;
import com.kakao.cafe.repository.article.MemoryArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleService {

    private final JdbcArticleRepository articleRepository;

    @Autowired
    public ArticleService(JdbcArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void join(ArticleForm form) {
        articleRepository.save(ArticleForm.toEntity(form));
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    public Article findArticle(Integer id) {
        return articleRepository.findByName(id).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 게시글입니다.")
        );
    }
}
