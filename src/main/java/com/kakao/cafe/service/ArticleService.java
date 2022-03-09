package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;

import java.util.List;

public interface ArticleService {

    List<Article> searchAll();
    Article add(Article article);
}
