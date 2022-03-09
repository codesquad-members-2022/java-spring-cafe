package com.kakao.cafe.repositoryimpl;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.VolatilityArticleRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class VolatilityArticleRepositoryImpl extends VolatilityArticleRepository {

    @Override
    public List<Article> findAll() {
        return Collections.unmodifiableList(articles);
    }

    @Override
    public synchronized Optional<Article> save(Article article) {
        Article result = persist(article);
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Article> findOne(Integer index) {
        return Optional.ofNullable(articles.get(index - 1));
    }

    @Override
    public void clear() {
        articles.clear();
    }
}
