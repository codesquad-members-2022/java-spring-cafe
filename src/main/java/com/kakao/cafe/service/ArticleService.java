package com.kakao.cafe.service;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.article.ArticleRepository;
import com.kakao.cafe.exception.ClientException;
import com.kakao.cafe.web.dto.ArticleDto;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void write(Article article) {
        articleRepository.save(article);
    }

    public ArticleDto findOne(int id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> {
            throw new ClientException(HttpStatus.BAD_REQUEST, "게시글을 찾을 수 없습니다.");
        });
        return new ArticleDto(article);

    }

    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream().map(ArticleDto::new).collect(Collectors.toList());
    }

    public void clearRepository() {
        articleRepository.clear();
    }
}
