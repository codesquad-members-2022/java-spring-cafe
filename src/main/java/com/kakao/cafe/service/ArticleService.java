package com.kakao.cafe.service;

import com.kakao.cafe.controller.dto.ArticleSaveRequestDto;
import com.kakao.cafe.domain.posts.Article;
import com.kakao.cafe.domain.posts.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void save(ArticleSaveRequestDto requestDto) {
        articleRepository.save(requestDto.toEntity());
    }

    public List<Article> findAllDesc() {
        return articleRepository.findAllDesc();
    }

    public Article findById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다."));
    }
}
