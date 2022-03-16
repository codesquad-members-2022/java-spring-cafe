package com.kakao.cafe.service;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.article.ArticleDto;
import com.kakao.cafe.repository.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleService {

    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public Article createArticle(ArticleDto articleDto) {
        isBlank(articleDto);
        return repository.save(articleDto.convertToArticle());
    }

    public Article findSingleArticle(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 아티클이 존재하지 않습니다."));
    }

    public List<Article> findAllArticle() {
        return repository.findAll();
    }

    private void isBlank(ArticleDto dto) {
        if (dto.getTitle().isEmpty() || dto.getContents().isEmpty()) {
            throw new IllegalArgumentException("제목 혹은 컨텐츠가 비어있습니다.");
        }
    }
}
