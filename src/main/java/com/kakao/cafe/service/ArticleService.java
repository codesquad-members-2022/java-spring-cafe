package com.kakao.cafe.service;

import com.kakao.cafe.controller.dto.PostingRequestDto;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public int posting(PostingRequestDto postingRequestDto) {
        Article newArticle = postingRequestDto.toEntity();
        return articleRepository.save(newArticle);
    }

    public List<Article> findPosts() {
        return articleRepository.findAll();
    }
}
