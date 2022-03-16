package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import java.util.List;

public interface ArticleRepository {

    int save(Article article);

    Article findById(int id);

    List<Article> findAll();
}
