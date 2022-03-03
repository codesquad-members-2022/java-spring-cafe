package com.kakao.cafe.web.service;

import com.kakao.cafe.web.domain.article.Article;
import com.kakao.cafe.web.repository.article.ArticleRepository;
import java.util.List;
import java.util.Optional;

public class ArticleService {

	private final ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public void save(Article article) {
		articleRepository.save(article);
	}

	public List<Article> findAllArticle() {
		return articleRepository.findAll();
	}

	public Optional<Article> findById(Long id) {
		return articleRepository.findById(id);
	}

}
