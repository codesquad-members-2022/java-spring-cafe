package com.kakao.cafe.repository;

import com.kakao.cafe.controller.ArticleDTO;
import com.kakao.cafe.domain.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleMemoryRepository implements ArticleRepository {

  private final ArrayList<Article> store = new ArrayList<>();

  @Override
  public Article save(Article article) {
    store.add(new Article(nextIndex(), article));
    return article;
  }

  @Override
  public Article findByIndex(Integer id) {
    return store.get(id - 1);
  }

  @Override
  public List<Article> findAll() {
    return new ArrayList<>(store);
  }

  @Override
  public void delete() {
    store.clear();
  }

  public Integer nextIndex() {
    return store.size() + 1;
  }
}
