package com.kakao.cafe.repository.article;

import com.kakao.cafe.domain.article.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleMemoryRepository implements ArticleRepository {

    private List<Article> store = new ArrayList<>();

    @Override
    public Article save(Article article) {
        article.setId(store.size() + 1L);
        store.add(article);

        return article;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return store.stream().filter(article -> article.getId().equals(id)).findAny();
    }

    @Override
    public List<Article> findAll() {
        return Collections.unmodifiableList(store);
    }
}
