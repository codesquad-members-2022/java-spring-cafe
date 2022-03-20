package com.kakao.cafe.domain.articles;

import java.util.List;

public interface ArticlesRepository {
	public List<Articles> findAll();
	public void save(Articles article);
	public Articles findByArticleId(long articleId);
}
