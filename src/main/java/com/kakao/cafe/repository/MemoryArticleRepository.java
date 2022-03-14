package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryArticleRepository {

    private List<Article> store = new ArrayList<>();

    public Article save(Article article) {
        store.add(article);
        return article;
    }

    public List<Article> findArticles() {
        return store;
    }
}
