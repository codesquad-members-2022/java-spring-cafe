package com.kakao.cafe.repository.collections;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ArticleCollectionRepository implements ArticleRepository {

    private List<Article> articles = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Article save(Article article) {
        article.setArticleId(articles.size() + 1);
        articles.add(article);
        return article;
    }

    @Override
    public List<Article> findAll() {
        return articles;
    }

    @Override
    public Optional<Article> findById(Integer articleId) {
        return articles.stream()
            .filter(article -> article.equalsId(articleId))
            .findAny();
    }

    @Override
    public void deleteAll() {
        articles = new ArrayList<>();
    }
}
