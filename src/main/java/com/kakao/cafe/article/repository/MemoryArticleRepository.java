package com.kakao.cafe.article.repository;

import com.kakao.cafe.article.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryArticleRepository implements ArticleRepository {

    private final List<Article> articleRegistry = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Optional<Article> save(Article article) {
        article.setId(articleRegistry.size() + 1);
        articleRegistry.add(article);

        return Optional.of(article);
    }

    @Override
    public Optional<Article> findById(Integer savedId) {
        try {
            return Optional.of(articleRegistry.get(savedId - 1));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Article>> findAll() {
        return Optional.of(Collections.unmodifiableList(articleRegistry));
    }

    @Override
    public void deleteAll() {
        articleRegistry.clear();
    }

}
