package com.kakao.cafe.service;

import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.web.dto.ArticleListDto;
import com.kakao.cafe.web.dto.ArticleRegisterFormDto;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void register(ArticleRegisterFormDto articleRegisterFormDto) {
        articleRepository.save(articleRegisterFormDto.toEntity());
    }

    public List<ArticleListDto> showAll() {
        return articleRepository.findAll().stream()
            .map(ArticleListDto::new)
            .collect(Collectors.toList());
    }
}
