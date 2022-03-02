package com.kakao.cafe.qna.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.kakao.cafe.qna.infra.MemoryArticleRepository;

class ArticleServiceTest {
	public static final String TEST_WRITER = "작성자";
	public static final String TEST_TITLE = "제목";
	public static final String TEST_CONTENT = "내용";
	@Autowired
	ArticleService articleService;

	@Autowired
	ArticleRepository articleRepository;

	@BeforeEach
	void beforeEach() {
		this.articleRepository = new MemoryArticleRepository();
		this.articleService = new ArticleService(articleRepository);
	}

	@Test
	@DisplayName("글쓰기 작성한 내용을 DB에 저장 된 것을 확인한다.")
	void writing_article_test() {
		ArticleDto articleDto = getArticleDto(TEST_WRITER, TEST_TITLE, TEST_CONTENT);
		this.articleService.write(articleDto);

		Article actual = this.articleRepository.findById(1L).get();

		assertAll(() -> assertThat(actual.getId()).isNotZero(),
			() -> assertThat(actual.getWriter()).isNotBlank());
	}

	@ParameterizedTest
	@DisplayName("글쓰기한 작성자, 제목, 내용 중 null or 공백 등이 있을시에는 예외처리 한다.")
	@MethodSource("providerInvalidWritingArticle")
	void invalid_writing_article_exception_test(String writer, String title, String content) {
		ArticleDto articleDto = getArticleDto(writer, title, content);

		assertThatThrownBy(() -> this.articleService.write(articleDto))
			.isInstanceOf(IllegalArgumentException.class);
	}

	private static Stream<Arguments> providerInvalidWritingArticle() {
		return Stream.of(
			Arguments.of(null, TEST_TITLE, TEST_CONTENT),
			Arguments.of(TEST_WRITER, null, TEST_CONTENT),
			Arguments.of(TEST_WRITER, TEST_TITLE, null),
			Arguments.of(TEST_WRITER, "  ", TEST_CONTENT)
		);
	}

	private ArticleDto getArticleDto(String writer, String title, String content) {
		ArticleDto articleDto = new ArticleDto();
		articleDto.setWriter(writer);
		articleDto.setTitle(title);
		articleDto.setContents(content);
		return articleDto;
	}
}
