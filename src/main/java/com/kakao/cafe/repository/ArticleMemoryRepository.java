package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleMemoryRepository implements ArticleRepository {

  private final ArrayList<Article> store = new ArrayList<>();

  @Override
  public Article save(Article article) {
    store.add(article);
    return article;
  }

  @Override
  public List<Article> findAll() {
    return new ArrayList<>(store);
  }

  @Override
  public void delete() {
    store.clear();
  }
}
