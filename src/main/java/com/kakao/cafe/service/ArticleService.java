package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.ArticleRequestDto;
import com.kakao.cafe.dto.ArticleResponseDto;
import com.kakao.cafe.repository.ArticleRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article upload(ArticleRequestDto articleRequestDto) {
        return articleRepository.save(articleRequestDto.convertToDomain());
    }

    public List<ArticleResponseDto> findAll() {
        return articleRepository.findAll().stream().map(article -> article.convertToDto()).collect(
            Collectors.toList());
    }

    public ArticleResponseDto findOne(int id) {
        return articleRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당하는 글이 없습니다.")).convertToDto();
    }

    public void deleteAll() {
        articleRepository.clear();
    }
}
