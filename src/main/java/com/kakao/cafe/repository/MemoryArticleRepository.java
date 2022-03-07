package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private final Map<Long, Article> articleStore = new HashMap<>();
    private static long articleIdx = 0;

    @Override
    public void createArticle(Article article) {
        article.setArticleIdx(++articleIdx);
        articleStore.put(articleIdx, article);
    }

    @Override
    public List<Article> findAllArticle() {
        return new ArrayList<>(articleStore.values());
    }
}
