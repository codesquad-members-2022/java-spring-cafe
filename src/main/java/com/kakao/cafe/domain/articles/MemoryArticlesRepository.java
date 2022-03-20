package com.kakao.cafe.domain.articles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryArticlesRepository implements ArticlesRepository {

	private List<Articles> articlesList = new ArrayList<>();

	@Override
	public List<Articles> findAll() {
		return articlesList;
	}

	@Override
	public void save(Articles article) {
		article.setArticleId((long) (articlesList.size() + 1));
		article.setCreatedDate(LocalDateTime.now());
		articlesList.add(article);
	}

	@Override
	public Articles findByArticleId(long articleId) {
		return articlesList.stream()
			.filter(article -> articleId == article.getArticleId())
			.findAny()
			.orElseThrow();
	}
}
