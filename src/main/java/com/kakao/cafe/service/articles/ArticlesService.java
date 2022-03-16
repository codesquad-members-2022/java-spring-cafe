package com.kakao.cafe.service.articles;

import com.kakao.cafe.domain.articles.ArticlesRepository;
import com.kakao.cafe.domain.articles.Articles;
import org.springframework.stereotype.Service;

@Service
public class ArticlesService {

	private ArticlesRepository articlesRepository;

	public ArticlesService(ArticlesRepository articlesRepository) {
		this.articlesRepository = articlesRepository;
	}

	public void save(Articles articles) {
		articlesRepository.save(articles);
	}
}
