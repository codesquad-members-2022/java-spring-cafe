package com.kakao.cafe.service;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.article.ArticleDto;
import com.kakao.cafe.repository.article.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public ArticleDto createArticle(ArticleDto articleDto) {
        articleDto.isEmpty();
        return repository.save(articleDto.convertToArticle()).convertToDto();
    }

    public ArticleDto findSingleArticle(Integer id) {
        Article article = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 아티클이 존재하지 않습니다."));
        return article.convertToDto();
    }

    public List<ArticleDto> findAllArticle() {
        return repository.findAll().stream()
                .map(Article::convertToDto)
                .collect(Collectors.toList());
    }

}
