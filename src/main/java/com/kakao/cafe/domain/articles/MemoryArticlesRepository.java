package com.kakao.cafe.domain.articles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryArticlesRepository implements ArticlesRepository {

	private List<Articles> articlesList = new ArrayList<>();

	@Override
	public List<Articles> findAll() {
		return null;
	}

	@Override
	public void save(Articles article) {
		articlesList.add(article);

		System.out.println();
	}

	@Override
	public Optional<Articles> findByArticleId(int articleId) {
		return Optional.empty();
	}
}
