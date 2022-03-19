package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private final List<Article> store = new ArrayList<>();
    private static long sequence = 0L;

    @Override
    public void save(Article article) {
        article.setId(store.size() + 1);
        store.add(article);
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(store);
    }

    @Override
    public Optional<Article> findById(int id) {
        return store.stream()
            .filter(article -> article.getId() == id)
            .findAny();
    }

    @Override
    public void deleteAll() {
        store.clear();
    }



}
