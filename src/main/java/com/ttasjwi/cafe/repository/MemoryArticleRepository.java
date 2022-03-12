package com.ttasjwi.cafe.repository;

import com.ttasjwi.cafe.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private final Map<Long, Article> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    /**
     * @param article : 게시글 객체
     * @return article의 생성된 아이디
     */
    public Long save(Article article) {
        article.setArticleId(sequence.incrementAndGet());
        Long articleId = article.getArticleId();
        storage.put(articleId, article);
        return articleId;
    }

    public Optional<Article> findByArticleId(Long articleId) {
        return Optional.ofNullable(storage.get(articleId));
    }

    public List<Article> findAll() {
        return new ArrayList<>(storage.values());
    }

}
