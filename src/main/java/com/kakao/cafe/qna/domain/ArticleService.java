package com.kakao.cafe.qna.domain;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ArticleService {
	private final ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public void write(ArticleDto articleDto) {
		Article question = new Article(articleDto.getWriter(), articleDto.getTitle(), articleDto.getContents());
		articleRepository.save(question);
	}

	public List<Article> getAllArticles() {
		return articleRepository.findAll();
	}
}

