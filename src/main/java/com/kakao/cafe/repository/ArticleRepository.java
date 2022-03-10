package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.List;

public interface ArticleRepository {

    void save(Article article);
    void deleteAll();
    List<Article> findAll();
}
