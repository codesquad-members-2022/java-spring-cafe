package com.kakao.cafe.article.repository;

import com.kakao.cafe.article.domain.Article;
import com.kakao.cafe.article.exception.ArticleNotFoundException;
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
        if (article.getId() == null) {
            return createArticle(article);
        }

        return updateArticle(article);
    }

    @Override
    public Optional<Article> findById(Long savedId) {
        try {
            return Optional.of(articleRegistry.get(savedId.intValue() - 1));
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

    private Optional<Article> createArticle(Article article) {
        article.setId((long) (articleRegistry.size() + 1));
        articleRegistry.add(article);

        return Optional.of(article);
    }

    private Optional<Article> updateArticle(Article modifiedArticle) {
        Optional<Article> findResult = findById(modifiedArticle.getId());

        if (findResult.isPresent()) {
            Article beforeModified = findResult.orElseThrow();
            int index = articleRegistry.indexOf(beforeModified);
            articleRegistry.remove(beforeModified);

            modifiedArticle.updateModifiedDate();
            articleRegistry.add(index, modifiedArticle);
            return Optional.of(modifiedArticle);
        }

        return Optional.empty();
    }

}
