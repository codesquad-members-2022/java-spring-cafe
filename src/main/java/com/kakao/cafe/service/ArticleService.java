package com.kakao.cafe.service;

import com.kakao.cafe.controller.dto.ArticleDto;
import com.kakao.cafe.controller.dto.PostingRequestDto;
import com.kakao.cafe.domain.Article;
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

    public int posting(PostingRequestDto postingRequestDto) {
        Article newArticle = postingRequestDto.toEntity();
        return articleRepository.save(newArticle);
    }

    public ArticleDto read(int id) {
        try {
            Article article = articleRepository.findById(id);
            article.addViewCount();
            return new ArticleDto(article);
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException("해당 아이디를 가진 게시글이 존재하지 않습니다.");
        }
    }

    public List<ArticleDto> findPosts() {
        return articleRepository.findAll().stream()
            .map(ArticleDto::new)
            .collect(Collectors.toList());
    }
}
