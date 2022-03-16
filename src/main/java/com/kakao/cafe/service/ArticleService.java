package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.NewArticleParam;
import com.kakao.cafe.exception.article.DuplicateArticleException;
import com.kakao.cafe.exception.article.NoSuchArticleException;
import com.kakao.cafe.repository.CrudRepository;
import com.kakao.cafe.util.DomainMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kakao.cafe.message.ArticleDomainMessage.*;

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

    public Article search(int id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchArticleException(HttpStatus.OK, NO_SUCH_ARTICLE_MESSAGE));
    }
}
