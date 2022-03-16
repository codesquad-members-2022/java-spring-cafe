package com.kakao.cafe.domain.articles;

import java.util.List;
import java.util.Optional;

public interface ArticlesRepository {
	public List<Articles> findAll();
	public void save(Articles article);
	public Optional<Articles> findByArticleId(int articleId);
}
