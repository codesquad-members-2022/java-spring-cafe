package com.kakao.cafe.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.kakao.cafe.domain.Article;

class ArticleRepositoryTest {

	ArticleRepository articleRepository = new ArticleRepository();

	@Test
	@DisplayName("id가 일치하는 게시글을 반환한다.")
	void findById() {
		// given
		Article article1 = new Article("phil", "test", "test", articleRepository.calculateSize()+1);
		articleRepository.save(article1);
		Article article2 = new Article("phil2", "test", "test", articleRepository.calculateSize()+1);
		articleRepository.save(article2);
		// when
		Article result = articleRepository.findById(2);
		// then
		Assertions.assertThat(result).isEqualTo(article2);
	}
}
