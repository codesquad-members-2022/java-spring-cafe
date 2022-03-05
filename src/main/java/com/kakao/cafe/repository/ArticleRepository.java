package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.List;

public interface ArticleRepository {

    Article save(Article userArticle);
    Article findById(Integer id);
    List<Article> findAll();
    void clear();

}
