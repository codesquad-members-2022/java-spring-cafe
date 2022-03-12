package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.NewArticleParam;

import java.util.List;

public interface ArticleService {

    List<Article> searchAll();
    Article add(NewArticleParam newArticleParam);
    Article search(long id);
}
