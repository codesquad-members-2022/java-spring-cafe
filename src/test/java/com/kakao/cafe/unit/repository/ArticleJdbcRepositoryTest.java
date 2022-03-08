package com.kakao.cafe.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.jdbc.ArticleJdbcRepository;
import com.kakao.cafe.repository.jdbc.KeyHolderFactory;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@ExtendWith(MockitoExtension.class)
public class ArticleJdbcRepositoryTest {

    @InjectMocks
    ArticleJdbcRepository articleRepository;

    @Mock
    NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    KeyHolderFactory keyHolderFactory;

    @Mock
    QueryProps queryProps;

    Article article;

    @BeforeEach
    public void setUp() {
        article = new Article.Builder()
            .articleId(1)
            .writer("writer")
            .title("title")
            .contents("contents")
            .build();
    }

    @Test
    @DisplayName("질문 객체를 저장소에 저장한다")
    public void test() {
        // given
        KeyHolder keyHolder = new GeneratedKeyHolder(List.of(Map.of("articleId", 1)));

        given(queryProps.get(any()))
            .willReturn("");

        given(keyHolderFactory.newKeyHolder())
            .willReturn(keyHolder);

        given(jdbcTemplate.update(any(String.class), any(BeanPropertySqlParameterSource.class),
            any(KeyHolder.class)))
            .willReturn(1);

        // when
        Article savedArticle = articleRepository.save(article);

        // then
        assertThat(savedArticle.getArticleId()).isEqualTo(1);
        assertThat(savedArticle.getWriter()).isEqualTo("writer");
        assertThat(savedArticle.getTitle()).isEqualTo("title");
        assertThat(savedArticle.getContents()).isEqualTo("contents");
    }

    @Test
    @DisplayName("모든 질문 객체를 조회한다")
    public void findAllTest() {
        // given
        given(queryProps.get(any()))
            .willReturn("");

        given(jdbcTemplate.query(any(String.class), any(RowMapper.class)))
            .willReturn(List.of(article));

        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertThat(articles).containsExactlyElementsOf(List.of(article));
    }

    @Test
    @DisplayName("질문 id 로 질문 객체를 조회한다")
    public void findByArticleIdTest() {
        // given
        given(queryProps.get(any()))
            .willReturn("");

        given(jdbcTemplate.queryForObject(any(String.class), any(MapSqlParameterSource.class), any(
            RowMapper.class)))
            .willReturn(article);

        // when
        Optional<Article> findArticle = articleRepository.findById(article.getArticleId());

        // then
        assertThat(findArticle).hasValue(article);
    }

    @Test
    @DisplayName("존재하지 않는 질문 id 로 질문 객체를 조회한다")
    public void findNullTest() {
        // given
        given(queryProps.get(any()))
            .willReturn("");

        given(jdbcTemplate.queryForObject(any(String.class), any(MapSqlParameterSource.class), any(
            RowMapper.class)))
            .willThrow(EmptyResultDataAccessException.class);

        // when
        Optional<Article> findArticle = articleRepository.findById(article.getArticleId());

        // then
        assertThat(findArticle).isEqualTo(Optional.empty());
    }

}

