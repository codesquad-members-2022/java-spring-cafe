package com.kakao.cafe.repository;

import com.kakao.cafe.dto.UserArticle;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    UserArticle save(UserArticle userArticle);
    Optional<UserArticle> findById(Integer id);
    List<UserArticle> findAll();
    void clear();

}
