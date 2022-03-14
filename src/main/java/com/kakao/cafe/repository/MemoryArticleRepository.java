package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private List<Article> articles = new ArrayList<>();
    private int idSequence = 0;

    @Override
    public Article save(Article article) {
        article.setId(++idSequence);
        article.setWrittenTime();
        articles.add(article);
        return article;
    }

    @Override
    public Optional<Article> findByid(int id) {
        Article foundArticle = null;
        if (id <= idSequence) {
            foundArticle = articles.get(id);
        }
        return Optional.ofNullable(foundArticle);
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(this.articles);
    }
}
