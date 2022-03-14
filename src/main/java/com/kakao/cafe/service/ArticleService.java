package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.web.questions.dto.ArticleDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleService {
    public static final String ARTICLE_NOT_FOUND_EXCEPTION = "해당 게시물을 찾을 수 없습니다.";
    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public Long addArticle(Article article) {
        repository.save(article);
        return article.getId();
    }

    public Long addArticle(ArticleDto dto) {
        Article article = new Article(dto.getWriter(), dto.getTitle(), dto.getContents(), LocalDateTime.now());
        return addArticle(article);
    }

    public Article findArticleById(Long articleId) {
        return repository.findById(articleId)
                .orElseThrow(() -> new NotFoundException(ARTICLE_NOT_FOUND_EXCEPTION));
    }

    public List<Article> findArticles() {
        return repository.findAll();
    }

    public Long deleteArticle(Long articleId) {
        return repository.delete(articleId);
    }
}
