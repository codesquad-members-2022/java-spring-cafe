package com.ttasjwi.cafe.domain.article;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryArticleRepository implements ArticleRepository {

    private final Map<Long, Article> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    /**
     * @param article : 게시글 객체
     * @return article의 생성된 아이디
     */
    public Long save(Article article) {
        article.initArticleId(sequence.incrementAndGet());
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
