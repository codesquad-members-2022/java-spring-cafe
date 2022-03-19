package com.kakao.cafe.service;

import com.kakao.cafe.controller.ArticleDTO;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;

public class ArticleService {

  private final ArticleRepository articleRepository;

  public ArticleService(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public Article join(ArticleDTO articleDTO) {
    Article article = new Article(articleDTO.getWriter(), articleDTO.getTitle(),
        articleDTO.getContents());
    return article;
  }
}
