package com.kakao.cafe.repository;

import com.kakao.cafe.entity.Article;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ArticleMemorySaveRepository implements ArticleRepository {

    private final List<Article> articleStore = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Article articleSave(Article article) {
        int id = articleStore.size() + 1;
        article.setId(id);
        article.setDate(LocalDateTime.now());
        articleStore.add(article);
        return article;
    }

    @Override
    public List<Article> findAllArticle() {
        return Collections.unmodifiableList(articleStore);
    }

    @Override
    public Optional<Article> findArticleById(int articleId) {
        return articleStore.stream()
                .filter(article ->
                        article.isSameId(articleId)
                ).findAny();
    }

    public void clearStore() {
        articleStore.clear();
    }
}
