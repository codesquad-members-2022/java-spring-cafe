package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class ArticleMemoryRepository implements ArticleRepository{

    List<Article> articles = new CopyOnWriteArrayList<>();
    Long articleSequence = 0L;

    @Override
    public Long nextSequence() {
        return this.articleSequence;
    }

    private void addArticleSize() {
        articleSequence++;
    }

    @Override
    public Long save(Article article) {
        articles.add(article);
        addArticleSize();

        return article.getId();
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articles.stream()
                .filter(article -> article.isSameArticle(id))
                .findFirst();
    }

    @Override
    public List<Article> findAll() {
        return articles;
    }

    public void deleteAllArticles() {
        this.articles = new CopyOnWriteArrayList<>();
    }
}
