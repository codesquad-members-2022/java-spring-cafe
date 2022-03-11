package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private final List<Article> articles = new CopyOnWriteArrayList<>();

    @Override
    public void save(Article article) {
        articles.add(article);
    }

    @Override
    public List<Article> getArticles() {
        List<Article> tempArticleList = new ArrayList<>();
        tempArticleList.addAll(articles);
        return tempArticleList;
    }

    @Override
    public Article findById(int articleId) {
        return articles.stream().filter(i -> i.getArticleId()==articleId).findAny().get();
    }

    @Override
    public void clearStorage() {
        articles.clear();
    }
}
