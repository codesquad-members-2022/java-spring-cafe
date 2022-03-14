package com.kakao.cafe.service;

import com.kakao.cafe.controller.dto.ArticleDto;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public void save(ArticleDto articleDto) {
        User user = userRepository.findByUserId(articleDto.getUserId());
        Article article = new Article(user, articleDto.getTitle(), articleDto.getContents());
        articleRepository.save(article);
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    public Article findOne(int index) {
        return articleRepository.findByIndex(index);
    }
}
