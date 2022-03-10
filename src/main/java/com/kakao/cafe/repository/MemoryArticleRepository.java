package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private List<Article> articles = Collections.synchronizedList(new ArrayList<>());

    @Override
    public synchronized Article save(Article article) {
        article.setId(articles.size() + 1);
        articles.add(article);
        return article;
    }

    @Override
    public Article findById(int id) {
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
