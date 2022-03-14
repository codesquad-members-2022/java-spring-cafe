package com.kakao.cafe.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.kakao.cafe.domain.Article;

@Repository
public class ArticleRepository {

	private static final List<Article> articleList = new ArrayList<>();

	public void save(Article article) {
		articleList.add(article);
	}

	public List<Article> findAll() {
		return List.copyOf(articleList);
	}

	public Article findById(int id) {
		return articleList.get(id - 1);
	}

	public int calculateSize() {
		return articleList.size();
	}
}
