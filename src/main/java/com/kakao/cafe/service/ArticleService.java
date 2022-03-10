package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.domain.dto.ArticleForm;
import com.kakao.cafe.repository.ArticleRepository;

import java.util.List;
import java.util.Optional;

public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public int post(ArticleForm articleForm) {
        Article article = articleForm.createArticle();
        articleRepository.save(article);
        return article.getIndex();
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();

    }

    public ArticleForm findOneArticle(int index) {
        Article article = articleRepository.findByIndex(index)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        return new ArticleForm(article.getTitle(), article.getWriter(), article.getContents());
    }
}
