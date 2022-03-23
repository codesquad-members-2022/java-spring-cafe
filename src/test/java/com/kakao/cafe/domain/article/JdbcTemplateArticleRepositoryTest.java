package com.kakao.cafe.domain.article;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDateTime;
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
        article = new Article(null,"writer","title","contents");
    }

    @Test
    @DisplayName("게시글을 저장하면 id가 생성된다.")
    void saveTest() {
        int expectedId = 1;

        Article actual = jdbcTemplateArticleRepository.save(article);

        assertThat(actual.getId()).isEqualTo(expectedId);
        assertThat(actual.getWriter()).isEqualTo("writer");
        assertThat(actual.getTitle()).isEqualTo("title");
        assertThat(actual.getContents()).isEqualTo("contents");

    }

    @Test
    @DisplayName("id로 게시글을 찾을 수 있다.")
    void findByIdTest() {

        Article actual = jdbcTemplateArticleRepository.save(article);

        Article target = jdbcTemplateArticleRepository.findById(actual.getId()).orElseThrow();

        assertThat(target).isEqualTo(actual);

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

        Article actual = jdbcTemplateArticleRepository.save(article);

        List<Article> all = jdbcTemplateArticleRepository.findAll();

        assertThat(all.size()).isEqualTo(1);
        assertThat(all).contains(actual);

    }

    @Test
    @DisplayName("id가 같은 article은 save했을 때, update된다.")
    void updateTest() {

        Article previousArticle = jdbcTemplateArticleRepository.save(article);
        Article updateArticle = new Article(previousArticle.getId(), "writer", "newTitle", "newContents", LocalDateTime.now());
        Article updated = jdbcTemplateArticleRepository.save(updateArticle);

        Optional<Article> updatedArticleOptional = jdbcTemplateArticleRepository.findById(previousArticle.getId());
        assertThat(updatedArticleOptional.isPresent()).isTrue();
        Article actual = updatedArticleOptional.get();

        assertThat(actual).isEqualTo(updated);
        assertThat(actual.getWriter()).isEqualTo(previousArticle.getWriter());
        assertThat(actual.getTitle()).isEqualTo("newTitle");
        assertThat(actual.getContents()).isEqualTo("newContents");
        assertThat(actual.getWrittenTime()).isEqualTo(previousArticle.getWrittenTime());

    }

    @Test
    @DisplayName("delte 시 해당 게시글을 삭제한다.")
    void deleteTest() {

        Article saved = jdbcTemplateArticleRepository.save(article);
        Integer id = saved.getId();

        assertThat(jdbcTemplateArticleRepository.findById(id)).isNotEmpty();
        boolean deleted = jdbcTemplateArticleRepository.deleteOne(id);

        assertThat(deleted).isTrue();
        assertThat(jdbcTemplateArticleRepository.findById(id)).isEmpty();
    }

}
