package com.kakao.cafe.service;

import com.kakao.cafe.entity.Article;
import com.kakao.cafe.entity.User;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final Logger log = LoggerFactory.getLogger(ArticleService.class);
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public String generateArticle(Article article) {
        validateExistingUser(article);
        articleRepository.articleSave(article);
        log.info("generate Article = {}", article);
        return article.getTitle();
    }

    private void validateExistingUser(Article article) {
        Optional<User> user = userRepository.findUserId(article.getWriter());
        if (user.isEmpty()) {
            log.info("등록되지 않은 유저가 글 작성을 시도");
            throw new IllegalArgumentException("등록되지 않은 유저는 글을 작성할 수 없습니다.");
        }
        log.info("등록된 유저가 글 작성을 시도");
    }

    public List<Article> findArticles() {
        return articleRepository.findAllArticle();
    }

    public Article findArticleById(int articleId) {
        return articleRepository.findArticleById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 번호입니다."));
    }
}
