package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void createArticle(Article article) {
        articleRepository.createArticle(article);
    }

    public List<Article> findAllArticle() {
        return articleRepository.findAllArticle();
    }

    public Article findArticle(long articleIdx) {
        return articleRepository.findArticle(articleIdx)
                .orElseThrow(() -> new IllegalStateException("찾을 수 없는 질문입니다."));
    }
}
