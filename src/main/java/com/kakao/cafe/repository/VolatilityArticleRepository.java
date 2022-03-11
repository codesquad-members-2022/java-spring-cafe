package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

@Service
public class VolatilityArticleRepository implements Repository<Article, Integer> {

    private final Vector<Article> articles = new Vector<>();

    @Override
    public List<Article> findAll() {
        return Collections.unmodifiableList(articles);
    }

    @Override
    public synchronized Optional<Article> save(Article article) {
        return Optional.ofNullable(persist(article));
    }
    private Article persist(Article article) {
        article.setId(articles.size() + 1);
        return articles.add(article) ? article : null;
    }

    @Override
    public Optional<Article> findOne(Integer index) {
        return Optional.ofNullable(articles.get(index - 1));
    }

    @Override
    public void clear() {
        articles.clear();
    }
}
