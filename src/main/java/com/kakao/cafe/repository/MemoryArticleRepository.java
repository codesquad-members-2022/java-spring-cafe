package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryArticleRepository implements ArticleRepository {

    private static List<Article> articles = new ArrayList<>();

    @Override
    public Article saveArticle(Article article) {
        article.setId(generateId());
        articles.add(article);
        return article;
    }

    private long generateId(){
        return articles.size() + 1;
    }

    @Override
    public List<Article> loadAllArticles() {
        return articles;
    }

    @Override
    public Optional<Article> loadOneArticle(Long index) {
        return articles.stream().filter(article -> article.isTheSameId(index-1)).findFirst();
    }
}
