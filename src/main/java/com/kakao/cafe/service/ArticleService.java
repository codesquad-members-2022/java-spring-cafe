package com.kakao.cafe.service;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.article.ArticleRepository;
import com.kakao.cafe.exception.ClientException;
import com.kakao.cafe.web.dto.ArticleDto;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleService {

    private final ArticleRepository postsRepository;

    public ArticleService(ArticleRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    public void write(Article posts) {
        postsRepository.save(posts);
    }

    public ArticleDto findOne(int id) {
        Article posts = postsRepository.findById(id).orElseThrow(() -> {
            throw new ClientException(HttpStatus.BAD_REQUEST, "게시글을 찾을 수 없습니다.");
        });
        return new ArticleDto(posts);

    }

    public List<ArticleDto> findAll() {
        return postsRepository.findAll().stream().map(ArticleDto::new).collect(Collectors.toList());
    }

    public void clearRepository() {
        postsRepository.clear();
    }
}
