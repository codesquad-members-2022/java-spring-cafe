package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static List<Article> articles = new ArrayList<>();

    @Override
    public Article saveArticle(Article article) {
        articles.add(article);
        return article;
    }

    @Override
    public List<Article> loadAllArticles() {
        return articles;
    }
}
