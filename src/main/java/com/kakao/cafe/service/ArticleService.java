package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.repository.JdbcArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;

    @Autowired
    public ArticleService(JdbcArticleRepository jdbcArticleRepository, UserService userService) {
        this.articleRepository = jdbcArticleRepository;
        this.userService = userService;
    }

    public List<Article> findAllArticle() {
        return articleRepository.getArticles();
    }

    public Article findOneArticle(int articleId) {
        return articleRepository.findById(articleId);
    }


    public void createArticle(Article article) {
        validUser(article.getWriter());
        articleRepository.save(article);
    }

    private void validUser(String userId) {
        if (userService.findOne(userId)==null) {
            throw new NullPointerException();
        }
    }

    public synchronized int getRepositorySize() {
        return articleRepository.getArticles().size();
    }

}
