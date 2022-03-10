package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private final Map<Long, Article> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public void save(Article article) {
        article.setId(++sequence);
        store.put(article.getId(), article);
    }

    @Override
    public void deleteAll() {
        store.clear();
    }



}
