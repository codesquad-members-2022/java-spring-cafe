package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

public class MemoryArticleRepository implements ArticleRepository {

    private static final List<Article> store = Collections.synchronizedList(new ArrayList<>());

    @Override
    public int save(Article article) {
        Article newArticle = new Article(nextId(), article);
        store.add(newArticle);
        return store.size();
    }

    @Override
    public Optional<Article> findById(int id) {
        try {
            return Optional.ofNullable(store.get(id - 1));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Article> findAll() {
        return List.copyOf(store);
    }

    public int nextId() {
        return store.size() + 1;
    }

    public void clear() {
        store.clear();
    }
}
