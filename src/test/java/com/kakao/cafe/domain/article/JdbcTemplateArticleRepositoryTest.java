package com.kakao.cafe.domain.article;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@JdbcTest
class JdbcTemplateArticleRepositoryTest {


    private final JdbcTemplateArticleRepository jdbcTemplateArticleRepository;
    private Article article;

    @Autowired
    public JdbcTemplateArticleRepositoryTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplateArticleRepository = new JdbcTemplateArticleRepository(namedParameterJdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        article = new Article("writer","title","contents");
    }

    @Test
    @DisplayName("id로 게시글을 찾을 수 있다.")
    void findByIdTest() {

        jdbcTemplateArticleRepository.save(article);
        int targetId = 1;

        Article target = jdbcTemplateArticleRepository.findById(targetId).orElseThrow();

        assertThat(target.getId()).isEqualTo(targetId);
        assertThat(target.getWriter()).isEqualTo("writer");
        assertThat(target.getTitle()).isEqualTo("title");
        assertThat(target.getContents()).isEqualTo("contents");

    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10})
    @DisplayName("찾는 게시글의 id가 없을 경우 빈 객체를 반환한다.")
    void findByIdThrowTest(int id) {

        Optional<Article> target = jdbcTemplateArticleRepository.findById(id);

        assertThat(target).isEqualTo(Optional.empty());

    }

    @Test
    @DisplayName("저장된 모든 게시글을 찾을 수 있다.")
    void findAllTest() {

        jdbcTemplateArticleRepository.save(article);

        List<Article> all = jdbcTemplateArticleRepository.findAll();

        assertThat(all.size()).isEqualTo(1);
        Article target = all.get(0);
        assertThat(target.getWriter()).isEqualTo("writer");
        assertThat(target.getTitle()).isEqualTo("title");
        assertThat(target.getContents()).isEqualTo("contents");

    }

}
