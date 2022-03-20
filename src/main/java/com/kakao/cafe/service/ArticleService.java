package com.kakao.cafe.service;

import com.kakao.cafe.controller.ArticleDTO;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import java.util.ArrayList;
import java.util.List;

public class ArticleService {

  private final ArticleRepository articleRepository;

  public ArticleService(ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public Article join(ArticleDTO articleDTO) {
    Article article = new Article(articleDTO.getWriter(), articleDTO.getTitle(),
        articleDTO.getContents());
    return articleRepository.save(article);
  }

  public List<ArticleDTO> findAllArticleDTO() {
    List<Article> articles = articleRepository.findAll();
    List<ArticleDTO> articleDTOList = new ArrayList<>();
    for (int i = 0; i < articles.size(); i++) {
      articleDTOList.add(ArticleDTO.from(articles.get(i)));
    }
    return articleDTOList;
  }

  public ArticleDTO findOneByIndex(Integer id) {
    return ArticleDTO.from(articleRepository.findByIndex(id));
  }
}
