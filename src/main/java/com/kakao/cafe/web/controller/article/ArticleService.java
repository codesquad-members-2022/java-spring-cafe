package com.kakao.cafe.web.controller.article;

import com.kakao.cafe.core.domain.article.Article;
import com.kakao.cafe.core.repository.article.ArticleRepository;
import com.kakao.cafe.web.controller.article.dto.PostWriteRequest;

import java.util.List;

public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findById(int id) {
        return articleRepository.findById(id).orElseThrow();
    }

    public void write(Article article) {
        articleRepository.save(article);
    }

    public ProfileEditResponse edit(PostEditRequest request) {
//        articleRepository.save()
        return null;
    }
}
