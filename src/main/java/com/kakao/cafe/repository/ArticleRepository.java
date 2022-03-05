package com.kakao.cafe.repository;

import com.kakao.cafe.domain.UserArticle;
import java.util.List;

public interface ArticleRepository {

    UserArticle save(UserArticle userArticle);
    UserArticle findById(Integer id);
    List<UserArticle> findAll();
    void clear();

}
