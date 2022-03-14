package com.kakao.cafe.repository.article;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.repository.KeyHolderGenerator;

@ExtendWith(MockitoExtension.class)
class H2ArticleRepositoryTest {

    @InjectMocks
    H2ArticleRepository articleRepository;

    @Mock
    NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    KeyHolderGenerator keyHolderGenerator;

    private Article article;

    @BeforeEach
    void init() {
        article = new Article("lee", "title1", "contents1");
        article.setId(1L);
        article.writeWhenCreated(LocalDateTime.now());
    }

    @DisplayName("Article 저장 후 auto increment된 id 1이 반환된다.")
    @Test
    void article_save() {
        // given
        KeyHolder keyHolder = new GeneratedKeyHolder(List.of(Map.of("id", 7)));

        given(keyHolderGenerator.getKeyHolder())
            .willReturn(keyHolder);

        // when
        Long id = articleRepository.save(article);

        // then
        assertThat(id).isEqualTo(7);
    }

    @DisplayName("저장된 id를 통해 Article을 가져올 수 있다.")
    @Test
    void article_find() {
        // given
        given(jdbcTemplate.queryForObject(
            any(String.class),
            any(SqlParameterSource.class),
            any(RowMapper.class)
        )).willReturn(article);

        // when
        Optional<Article> optionalArticle = articleRepository.findById(article.getId());

        // then
        Article result = optionalArticle.orElseThrow();
        assertThat(article).isEqualTo(result);
    }

    @DisplayName("findAll을 통해 전체 Article List를 가져올 수 있다.")
    @Test
    void article_findAll() {
        // given
        given(jdbcTemplate.query(
            any(String.class),
            any(RowMapper.class)
        )).willReturn(List.of(article));

        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertThat(articles.size()).isEqualTo(1);
    }
}
