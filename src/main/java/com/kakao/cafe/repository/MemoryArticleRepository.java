package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private final Map<Long, Article> store = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public Long save(Article article) {
        article.setId(++sequence);
        store.put(article.getId(), article);
        return article.getId();
    }

    @Override
    public Optional<Article> findById(Long id) {
        return store.keySet().stream()
                .filter(key -> key.equals(id))
                .map(store::get)
                .findAny();
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean delete(Long id) {
        if (store.containsKey(id)) {
            store.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public void update(Long id, Article article) {
        if (findById(id).isPresent()) {
            Article find = findById(id).get();
            find.setTitle(article.getTitle());
            find.setContent(article.getContent());
        }
    }
}
