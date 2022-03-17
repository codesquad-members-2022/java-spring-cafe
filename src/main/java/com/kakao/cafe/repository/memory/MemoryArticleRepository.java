package com.kakao.cafe.repository.memory;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MemoryArticleRepository implements ArticleRepository {
    private final List<Article> articles = new CopyOnWriteArrayList<>();

    @Override
    public Article save(Article article) {
        int articleId = articles.size() + 1;
        Article saveArticle = new Article(articleId, article);
        articles.add(saveArticle);
        return saveArticle;
    }

    @Override
    public List<Article> findAll() {
        return articles;
    }

    @Override
    public Article findById(int id) {
        return articles.get(id - 1);
    }

}
