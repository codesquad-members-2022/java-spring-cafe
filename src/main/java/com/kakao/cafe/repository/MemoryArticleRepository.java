package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.ArrayList;
import java.util.List;

public class MemoryArticleRepository implements ArticleRepository {

    private List<Article> articles = new ArrayList<>();

    @Override
    public Article save(Article article) {
        article.setId(articles.size() + 1);
        articles.add(article);
        return article;
    }

    @Override
    public Article findById(Integer id) {
        return articles.get(id - 1);
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articles);
    }

    @Override
    public void clear() {
        articles.clear();
    }
}
