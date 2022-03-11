package com.kakao.cafe.qna.infra;

import static com.kakao.cafe.qna.domain.ArticleServiceTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kakao.cafe.qna.domain.Article;

class MemoryArticleRepositoryTest {
	@Autowired
	private MemoryArticleRepository memoryArticleRepository;

	@BeforeEach
	void beforeEach() {
		memoryArticleRepository = new MemoryArticleRepository();
	}

	@AfterEach
	void afterEach() {
		memoryArticleRepository.deleteAll();
	}

	@Test
	@DisplayName("작성된 글의 DB 저장을 확인한다.")
	void save_article() {
		Article expected = getArticle();
		Article article = memoryArticleRepository.save(expected);

		Optional<Article> actual = memoryArticleRepository.findById(article.getId());
		assertThat(actual.isPresent()).isTrue();
		assertThat(actual.get().getId()).isNotZero();
	}

	@Test
	@DisplayName("수정된 글의 DB 업데이트를 확인한다.")
	void update_article() {
		String changedTitle = "제목 변경 테스트";
		Article testArticle = getArticle();
		memoryArticleRepository.save(testArticle);
		testArticle.changeTitle(changedTitle);
		Article actual = memoryArticleRepository.save(testArticle);

		assertThat(actual.getId()).isNotZero();
		assertThat(actual.getTitle()).isNotEqualTo(TEST_TITLE);
	}

	public Article getArticle() {
		return new Article(TEST_WRITER, TEST_TITLE, TEST_CONTENT);
	}

}
