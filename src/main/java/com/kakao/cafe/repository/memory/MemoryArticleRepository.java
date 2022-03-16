package com.kakao.cafe.repository.memory;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private final List<Article> articles = new CopyOnWriteArrayList<>();

    @Override
    public void save(Article article) {
        article.setId(articles.size() + 1);
        articles.add(article);
    }

    @Override
    public List<Article> findAll() {
        return articles;
    }

    @Override
    public Article findById(int id) {
        return articles.get(id - 1);
    }

}
