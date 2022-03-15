package com.kakao.cafe.service;

import com.kakao.cafe.controller.ArticleForm;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;

import java.util.List;
import java.util.Optional;

public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * 게시글 작성하기
     */
    public Article write(ArticleForm articleForm) {
        int articleId = articleRepository.size() + 1;
        Article article = new Article(articleForm, articleId);
        return articleRepository.save(article);
    }

    /**
     * 전체 게시글 불러오기
     */
    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    /**
     * 게시글 찾기
     */
    public Article findArticle(int id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        return validateNull(optionalArticle);
    }

    private Article validateNull(Optional<Article> optionalArticle) {
        return optionalArticle.orElseThrow(() -> {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        });
    }
}
