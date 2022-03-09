package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import java.util.Vector;

public abstract class VolatilityArticleRepository implements Repository<Article, Integer> {

    protected final Vector<Article> articles = new Vector<>();

    protected Article persist(Article article) {
        article.setId(Integer.toString(articles.size() + 1));

        return articles.add(article) ? article : null;
    }
}
