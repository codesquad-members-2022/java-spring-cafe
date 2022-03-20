package com.kakao.cafe.service;

import com.kakao.cafe.entity.Article;
import com.kakao.cafe.entity.User;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public String registerArticle(Article article) {
        validateExistingUser(article);
        articleRepository.articleSave(article);
        return article.getTitle();
    }

    private void validateExistingUser(Article article) {
        Optional<User> user = userRepository.findUserId(article.getWriter());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("등록되지 않은 유저는 글을 작성할 수 없습니다.");
        }
    }

    public List<Article> findArticles() {
        return articleRepository.findAllArticle();
    }

    public Article findArticleById(int articleId) {
        return articleRepository.findArticleById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물 번호입니다."));
    }
}
