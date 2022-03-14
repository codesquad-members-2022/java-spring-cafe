package com.kakao.cafe.qna.infra;

import static com.kakao.cafe.qna.domain.ArticleServiceTest.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kakao.cafe.qna.domain.Article;
import com.kakao.cafe.qna.domain.ArticleFactory;

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

		Optional<Article> actual = memoryArticleRepository.findById(article.getArticleId());
		assertThat(actual.isPresent()).isTrue();
		assertThat(actual.get().getArticleId()).isNotZero();
	}

	@Test
	@DisplayName("수정된 글의 DB 업데이트를 확인한다.")
	void update_article() {
		String changedTitle = "제목 변경 테스트";
		Article testArticle = getArticle();
		memoryArticleRepository.save(testArticle);
		testArticle.changeTitle(changedTitle);
		Article actual = memoryArticleRepository.save(testArticle);

		assertThat(actual.getArticleId()).isNotZero();
		assertThat(actual.getTitle()).isNotEqualTo(TEST_TITLE);
	}

	@Test
	@DisplayName("글의 삭제 처리 후 삭제 됨을 확인한다.")
	void delete_article() {
		Article testArticle = getArticle();
		Article expected = memoryArticleRepository.save(testArticle);

		memoryArticleRepository.delete(expected.getArticleId());

		Optional<Article> actual = memoryArticleRepository.findById(expected.getArticleId());
		assertThat(actual.isEmpty()).isTrue();
	}

	public Article getArticle() {
		return ArticleFactory.create(TEST_WRITER, TEST_TITLE, TEST_CONTENT, 1L);
	}
}
