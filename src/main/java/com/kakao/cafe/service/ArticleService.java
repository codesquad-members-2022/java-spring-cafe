package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.ModifiedArticleParam;
import com.kakao.cafe.dto.NewArticleParam;
import com.kakao.cafe.exception.article.DuplicateArticleException;
import com.kakao.cafe.exception.article.NoSuchArticleException;
import com.kakao.cafe.exception.article.RemoveArticleException;
import com.kakao.cafe.exception.user.SaveUserException;
import com.kakao.cafe.repository.CrudRepository;
import com.kakao.cafe.util.DomainMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kakao.cafe.message.ArticleDomainMessage.*;
import static com.kakao.cafe.message.UserDomainMessage.UPDATE_FAIL_MESSAGE;

@Service
public class ArticleService {

    private final CrudRepository<Article, Integer> articleRepository;
    private final DomainMapper<Article> articleMapper = new DomainMapper<>();

    public ArticleService(CrudRepository<Article, Integer> articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> searchAll() {
        return articleRepository.findAll();
    }

    public Article add(NewArticleParam newArticleParam) {
        Article newArticle = articleMapper.convertToDomain(newArticleParam, Article.class);
        return articleRepository.save(newArticle)
                .orElseThrow(() -> new DuplicateArticleException(HttpStatus.OK, DUPLICATE_ARTICLE_MESSAGE));
    }

    public Article update(ModifiedArticleParam modifiedArticleParam) {
        Article article = articleMapper.convertToDomain(modifiedArticleParam, Article.class);
        return articleRepository.save(article)
                .orElseThrow(() -> new SaveUserException(HttpStatus.BAD_GATEWAY, UPDATE_FAIL_MESSAGE));
    }

    public void remove(int id) {
        int resultCount = articleRepository.deleteById(id);
        validateResultCount(resultCount);
    }

    private void validateResultCount(int resultCount) {
        if (resultCount == 0) {
            throw new RemoveArticleException(HttpStatus.BAD_GATEWAY, DELETE_FAIL_MESSAGE);
        }
    }

    public Article search(int id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchArticleException(HttpStatus.OK, NO_SUCH_ARTICLE_MESSAGE));
    }
}
