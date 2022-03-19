package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private final List<Article> articles = Collections.synchronizedList(new ArrayList<>());

    @Override
    public synchronized Article save(Article article) {
        article.setId(articles.size() + 1);
        articles.add(article);
        return article;
    }

    @Override
    public Optional<Article> findById(int id) {
        return Optional.ofNullable(articles.get(id - 1));
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteAll() {
        articles.clear();
    }
}
