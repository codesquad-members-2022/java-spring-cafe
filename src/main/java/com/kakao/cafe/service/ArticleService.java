package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.NewArticleParam;
import com.kakao.cafe.exception.article.DuplicateArticleException;
import com.kakao.cafe.exception.article.NoSuchArticleException;
import com.kakao.cafe.repository.DomainRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kakao.cafe.message.ArticleDomainMessage.*;

@Service
public class ArticleService {

    private final DomainRepository<Article, Long> articleRepository;

    public ArticleService(DomainRepository<Article, Long> articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> searchAll() {
        return articleRepository.findAll();
    }

    public Article add(NewArticleParam newArticleParam) {
        return articleRepository.save(newArticleParam.convertToArticle())
                .orElseThrow(() -> new DuplicateArticleException(HttpStatus.OK, DUPLICATE_ARTICLE_MESSAGE));
    }

    public Article search(long id) {
        return articleRepository.findOne(id)
                .orElseThrow(() -> new NoSuchArticleException(HttpStatus.OK, NO_SUCH_ARTICLE_MESSAGE));
    }
}
