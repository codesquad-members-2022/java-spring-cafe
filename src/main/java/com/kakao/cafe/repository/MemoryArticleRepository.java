package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private static final List<Article> store = Collections.synchronizedList(new ArrayList<>());

    @Override
    public int save(Article article) {
        Article newArticle = new Article(nextId(), article);
        store.add(newArticle);
        return store.size();
    }

    @Override
    public Article findById(int id) {
        return store.get(id - 1);
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
