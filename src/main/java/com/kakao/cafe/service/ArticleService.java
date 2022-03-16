package com.kakao.cafe.service;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.article.ArticleRepository;
import com.kakao.cafe.exception.ClientException;
import com.kakao.cafe.web.dto.ArticleDto;
import com.kakao.cafe.web.dto.ArticleResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article write(String writer, ArticleDto articleDto) {
        return articleRepository.save(articleDto.toEntity(writer));
    }

    public ArticleResponseDto findOne(int id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> {
            throw new ClientException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");
        });
        return new ArticleResponseDto(article);

    }

    public List<ArticleResponseDto> findAll() {
        return articleRepository.findAll().stream().map(ArticleResponseDto::new).collect(Collectors.toList());
    }

    public void clearRepository() {
        articleRepository.clear();
    }

    public void deleteOne(int ArticleId, String userId) {
        if(!findOne(ArticleId).isSameWriter(userId)) {
            throw new ClientException(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다.");
        }
        articleRepository.deleteOne(ArticleId);
    }
}
