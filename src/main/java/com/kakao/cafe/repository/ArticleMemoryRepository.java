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

    private Long nextSequence() {
        return this.articleSequence;
    }

    private void setArticleSequence(int sequence) {
        articleSequence = Math.max(articleSequence, sequence);
    }

    @Override
    public Article save(Article article) {
        Article saveArticle = new Article(nextSequence(), article.getWriter(), article.getTitle(), article.getContents(),
                article.getCreatedTime(), article.getUpdatedTime());

        articles.add(saveArticle);
        setArticleSequence(articles.size());

        return findById(saveArticle.getId()).orElse(saveArticle);
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
}
