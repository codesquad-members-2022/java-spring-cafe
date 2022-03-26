package com.kakao.cafe.service;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.article.ArticleRepository;
import com.kakao.cafe.exception.ClientException;
import com.kakao.cafe.web.dto.ArticleDto;
import com.kakao.cafe.web.dto.ArticleResponseDto;
import com.kakao.cafe.web.dto.ArticleUpdateDto;
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

    public boolean deleteOne(Integer articleId, String writer) {
        checkAuthorized(articleId, writer);
        return articleRepository.deleteOne(articleId);
    }

    public Article updateOne(String userId,  ArticleUpdateDto articleUpdateDto) {
        checkAuthorized(articleUpdateDto.getId(), userId);
        return articleRepository.save(articleUpdateDto.toEntityWithWriter(userId));
    }

    private void checkAuthorized(Integer articleId, String writer) {
        if(!findOne(articleId).isSameWriter(writer)) {
            throw new ClientException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }
}
