package com.kakao.cafe.service;

import com.kakao.cafe.Controller.dto.ArticleForm;
import com.kakao.cafe.Controller.dto.ArticleResponse;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final String NO_MATCH_ARTICLE_MESSAGE = "일치하는 질문이 없습니다.";
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Long save(ArticleForm article) {
        Long id = articleRepository.save(ArticleForm.toEntity(articleRepository.nextSequence(), article));

        return id;
    }

    private Article findById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NO_MATCH_ARTICLE_MESSAGE));
    }

    public ArticleResponse findArticleResponseById(Long id) {
        Article findArticle = findById(id);

        return ArticleResponse.of(findArticle);
    }

    public List<ArticleResponse> findAll() {
        return articleRepository.findAll().stream()
                .map(ArticleResponse::of)
                .collect(Collectors.toUnmodifiableList());
    }

    public void deleteAllArticles() {
        articleRepository.deleteAllArticles();
    }
}
