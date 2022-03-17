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
        return articleRepository.save(articleDto.toEntityWithWriter(writer));
    }

    public ArticleResponseDto findOne(Integer id) {
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

    public void deleteOne(Integer articleId, String userId) {
        checkAuthorized(articleId, userId);
        articleRepository.deleteOne(articleId);
    }

    public Article updateOne(String userId, String writer, Integer articleId, ArticleDto articleDto) {
        checkAuthorized(articleId, writer);
        return articleRepository.save(articleDto.toUpdateEntity(articleId, writer));
    }

    private void checkAuthorized(Integer articleId, String writer) {
        if(!findOne(articleId).isSameWriter(writer)) {
            throw new ClientException(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다.");
        }
    }
}
