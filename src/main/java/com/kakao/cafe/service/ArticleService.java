package com.kakao.cafe.service;

import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.web.dto.ArticleRegisterFormDto;

public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void register(ArticleRegisterFormDto articleRegisterFormDto) {
        articleRepository.save(articleRegisterFormDto.toEntity());
    }
}
