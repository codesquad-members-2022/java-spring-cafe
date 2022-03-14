package com.kakao.cafe.repository.memory;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryArticleRepository implements ArticleRepository {
    private final Map<Long, Article> articleMap = new ConcurrentHashMap<>();
    private Long sequence = 0L;

    @Override
    public Long save(Article article) {
        article.setId(++sequence);
        articleMap.put(article.getId(), article);
        return article.getId();
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(articleMap.get(id));
    }

    @Override
    public List<Article> findAll() {
        return new ArrayList<>(articleMap.values());
    }

    @Override
    public Long delete(Long id) {
        if (articleMap.containsKey(id)) {
            articleMap.remove(id);
            return id;
        }
        return -1L;
    }

    @Override
    public void update(Long id, Article article) {
        if (findById(id).isPresent()) {
            Article find = findById(id).get();
            find.setTitle(article.getTitle());
            find.setContents(article.getContents());
        }
    }
}
