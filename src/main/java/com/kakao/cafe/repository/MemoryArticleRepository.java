package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private final List<Article> articles = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Article save(Article article) {
        article.setId(articles.size() + 1);
        articles.add(article);
        return article;
    }

    @Override
    public List<Article> findAll() {
        return Collections.unmodifiableList(articles);
    }

    @Override
    public Optional<Article> findByArticleId(Integer id) {
        return articles.stream()
                .filter(article -> article.equalId(id))
                .findAny();
    }

    public void clear() {
        articles.clear();
    }
}
