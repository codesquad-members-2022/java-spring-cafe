package com.kakao.cafe.qna.domain;

import java.util.List;

import com.kakao.cafe.common.db.Repository;

public interface ArticleRepository extends Repository<Article, Long> {
	List<Article> findAll();

	void delete(Long id);
}
