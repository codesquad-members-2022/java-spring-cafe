package com.kakao.cafe.service;

import com.kakao.cafe.controller.dto.ArticleSaveRequestDto;
import com.kakao.cafe.controller.dto.ArticleUpdateRequestDto;
import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.article.ArticleRepository;
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

    public void update(ArticleUpdateRequestDto dto) {
        Long id = dto.getId();
        String title = dto.getTitle();
        String contents = dto.getContents();

        Article article = findById(id);
        Article updateArticle = article.update(title, contents);
        articleRepository.save(updateArticle);
    }
}
