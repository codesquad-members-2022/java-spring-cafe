package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.NotFoundException;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.web.questions.dto.ArticleDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleService {
    private static final String ARTICLE_NOT_FOUND_EXCEPTION = "해당 게시물을 찾을 수 없습니다.";
    private static final String UN_AUTHORIZATION_EXCEPTION = "해당 글을 수정할 권한이 없습니다.";
    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public Long addArticle(Article article) {
        repository.save(article);
        return article.getId();
    }

    public Long addArticle(ArticleDto dto, User user) {
        Article article = new Article(user.getUserId(), dto.getTitle(), dto.getContents(), LocalDateTime.now());
        return addArticle(article);
    }

    public Article findArticleById(Long articleId) {
        return repository.findById(articleId)
                .orElseThrow(() -> new NotFoundException(ARTICLE_NOT_FOUND_EXCEPTION));
    }

    public List<Article> findArticles() {
        return repository.findAll();
    }

    public ArticleDto findArticleDtoById(Long articleId, User sessionUser) {
        if (isUserHasAuthorization(articleId, sessionUser)) {
            Article findArticle = findArticleById(articleId);
            return new ArticleDto(findArticle.getUserId(), findArticle.getTitle(), findArticle.getContents());
        }
        throw new IllegalArgumentException(UN_AUTHORIZATION_EXCEPTION);
    }

    public Long deleteArticle(Long articleId, User sessionUser) {
        if (isUserHasAuthorization(articleId, sessionUser)) {
            return repository.delete(articleId);
        }
        throw new IllegalArgumentException(UN_AUTHORIZATION_EXCEPTION);
    }

    public void updateArticle(ArticleDto dto, Long articleId, User sessionUser) {
        if (isUserHasAuthorization(articleId, sessionUser)) {
            Article updateArticleForm = new Article(sessionUser.getUserId(), dto.getTitle(), dto.getContents(), LocalDateTime.now());
            updateArticleForm.setId(articleId);
            repository.update(articleId, updateArticleForm);
        }
        throw new IllegalArgumentException(UN_AUTHORIZATION_EXCEPTION);
    }

    private boolean isUserHasAuthorization(Long articleId, User sessionUser) {
        Article findArticle = findArticleById(articleId);

        if (!sessionUser.isOwnArticle(findArticle)) {
            return false;
        }
        return true;
    }
}
