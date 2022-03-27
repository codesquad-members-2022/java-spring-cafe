package com.kakao.cafe.repository.article;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@org.springframework.stereotype.Repository
public class MemoryArticleRepository implements Repository<Article, Integer> {

    private final List<Article> articles = new CopyOnWriteArrayList<>();

    @Override
    public Article save(Article article) {
        Article articleWithId = new Article(articles.size() + 1, article.getTitle(), article.getText());
        articles.add(articleWithId);
        return articleWithId;
    }

    @Override
    public Optional<Article> findByName(Integer id) {
        return Optional.ofNullable(articles.get(id));
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(List.copyOf(articles));
    }

    public void clearStore() {
        articles.clear();
    }
}
