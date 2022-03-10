package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class MemoryArticleRepository implements ArticleRepository{

    private final List<Article> articleList = new CopyOnWriteArrayList<>();

    @Override
    public void save(Article article) {
        articleList.add(article);
    }

    @Override
    public List<Article> getArticleList() {
        List<Article> tempArticleList = new ArrayList<>();
        tempArticleList.addAll(articleList);
        return tempArticleList;
    }

    @Override
    public Article findById(int articleId) {
        for (Article article : articleList) {
            if (article.getArticleId() == articleId) {
                return article;
            }
        }
        return null;
    }

    @Override
    public void clearStorage() {
        articleList.clear();
    }
}
