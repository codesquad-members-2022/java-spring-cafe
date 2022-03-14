package com.kakao.cafe.article.service;

import com.kakao.cafe.article.controller.dto.ArticleWriteRequest;
import com.kakao.cafe.article.domain.Article;
import com.kakao.cafe.article.exception.ArticleUnsavedException;
import com.kakao.cafe.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article write(ArticleWriteRequest writeRequest) {
        Article article = Article.createWithWriteRequest(writeRequest);

        return articleRepository.save(article)
                .orElseThrow(ArticleUnsavedException::new);
    }

}
