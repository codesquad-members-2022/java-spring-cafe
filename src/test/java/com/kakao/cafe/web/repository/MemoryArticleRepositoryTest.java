package com.kakao.cafe.web.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.kakao.cafe.web.domain.article.Article;
import com.kakao.cafe.web.repository.article.MemoryArticleRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemoryArticleRepositoryTest {

	MemoryArticleRepository repository = new MemoryArticleRepository();

	@AfterEach
	void tearDown() {
		repository.clear();
	}

	//todo
	// save가 잘 되었는지 확인을 하기 위해서는 저장소에서 꺼내오는 작업이 필요함
	// -> 동시에 save, 꺼내오는 과정(findById)를 수행하게 되는데 save만 확인하는 방법은 없을까?
	@Test
	@DisplayName("질문을 하면 저장소에 질문이 저장이 되어서 Id값을 통해 찾을 수 있다.")
	void saveArticleThenFindArticleById() {
		//given
		Article article = new Article("writer", "title", "contents");

		//when
		repository.save(article);
		Article result = repository.findById(article.getId()).orElseThrow(NoSuchElementException::new);

		//then
		assertThat(result).hasToString(String.valueOf(article));
	}

	@Test
	@DisplayName("질문들을 하면 저장소에 질문들이 저장이 되어서 한 번에 모든 질문들을 찾을 수 있다.")
	void saveArticlesThenFindAllArticle() {
		//given
		Article article1 = new Article("writer1", "title1", "contents1");
		Article article2 = new Article("writer2", "title2", "contents2");

		//when
		repository.save(article1);
		repository.save(article2);
		List<Article> result = repository.findAll();

		//then
		assertThat(result).hasSize(2);
		assertThat(result.contains(article1)).isTrue();
		assertThat(result.contains(article2)).isTrue();
	}

}
