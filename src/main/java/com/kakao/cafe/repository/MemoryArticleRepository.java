package com.kakao.cafe.repository;

import com.kakao.cafe.dto.UserArticle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryArticleRepository implements ArticleRepository {

    private List<UserArticle> userArticles = new ArrayList<>();

    @Override
    public UserArticle save(UserArticle userArticle) {
        userArticle.setId(userArticles.size() + 1);
        userArticles.add(userArticle);
        return userArticle;
    }

    @Override
    public Optional<UserArticle> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<UserArticle> findAll() {
        return new ArrayList<>(userArticles);
    }

    @Override
    public void clear() {
        userArticles.clear();
    }
}
