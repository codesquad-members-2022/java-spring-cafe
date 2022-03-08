package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.ArticleForm;
import com.kakao.cafe.exception.CustomException;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.util.Mapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article write(ArticleForm articleForm) {
        return articleRepository.save(Mapper.map(articleForm, Article.class));
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    public Article findArticle(Integer articleId) {
        return articleRepository.findById(articleId)
            .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
    }
}
