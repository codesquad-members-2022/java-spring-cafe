package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.MemoryArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private MemoryArticleRepository memoryArticleRepository;

    public ArticleService(MemoryArticleRepository memoryArticleRepository) {
        this.memoryArticleRepository = memoryArticleRepository;
    }

    public String write(Article article) {
        memoryArticleRepository.save(article);
        return article.getTitle();
    }

    public List<Article> findAll() {
        return memoryArticleRepository.findArticles();
    }
}
