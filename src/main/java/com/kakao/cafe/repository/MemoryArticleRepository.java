package com.kakao.cafe.repository;

import com.kakao.cafe.dto.UserArticle;
import java.util.ArrayList;
import java.util.List;

public class MemoryArticleRepository implements ArticleRepository {

    private List<UserArticle> userArticles = new ArrayList<>();

    @Override
    public UserArticle save(UserArticle userArticle) {
        userArticle.setId(userArticles.size() + 1);
        userArticles.add(userArticle);
        return userArticle;
    }

    @Override
    public UserArticle findById(Integer id) {
        return userArticles.get(id - 1);
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
