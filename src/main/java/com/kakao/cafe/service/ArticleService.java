package com.kakao.cafe.service;

import com.kakao.cafe.controller.dto.ArticleSaveDto;
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

    public void save(ArticleSaveDto articleSaveDto) {
        User user = userRepository.findByUserId(articleSaveDto.getUserId());
        Article article = new Article(user, articleSaveDto.getTitle(), articleSaveDto.getContents());
        articleRepository.save(article);
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    public Article findOne(int index) {
        return articleRepository.findByIndex(index);
    }
}
