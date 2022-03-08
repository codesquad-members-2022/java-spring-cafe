package com.kakao.cafe.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.jdbc.ArticleJDBCRepository;
import com.kakao.cafe.repository.jdbc.KeyHolderFactory;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@ExtendWith(MockitoExtension.class)
public class ArticleJDBCRepositoryTest {

    @InjectMocks
    ArticleJDBCRepository articleRepository;

    @Mock
    NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    KeyHolderFactory keyHolderFactory;

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
    public void test() {
        // given
        KeyHolder keyHolder = new GeneratedKeyHolder(List.of(Map.of("articleId", 1)));

        given(keyHolderFactory.newKeyHolder())
            .willReturn(keyHolder);

        given(jdbcTemplate.update(any(String.class), any(MapSqlParameterSource.class),
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

}
