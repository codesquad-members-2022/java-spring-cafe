package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Transactional
class ArticleJdbcRepositoryTest {

    private final ArticleJdbcRepository articleJdbcRepository;
    private Article article;

    @Autowired
    public ArticleJdbcRepositoryTest(DataSource dataSource) {
        this.articleJdbcRepository = new ArticleJdbcRepository(dataSource);
    }

    @BeforeEach
    void setUp() {
        article = new Article(1L, "ader", "title sample", "contents sample", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    @DisplayName("정상적인 Article의 저장을 시도하면 정상적으로 저장된다")
    void saveTest() {
        // given

        // when
        Article saveArticle = articleJdbcRepository.save(article);

        // then
        assertThat(saveArticle.isSameArticle(article.getId())).isTrue();
    }

    @Test
    @DisplayName("저장되어 있는 Article의 id로 검색을 시도하면 해당하는 Article을 리턴한다")
    void findByIdTest() {
        // given
        Article saveArticle = articleJdbcRepository.save(article);

        // when
        Article findArticle = articleJdbcRepository.findById(saveArticle.getId()).orElse(null);

        // then
        assertThat(findArticle.isSameArticle(saveArticle.getId())).isTrue();
    }

    @Test
    @DisplayName("저장되어 있지 않은 id로 검색을 시도하면 Optional.empty()를 리턴한다")
    void findByIdExceptionTest() {
        // given

        // when
        Optional<Article> findArticle = articleJdbcRepository.findById(2L);

        // then
        assertThat(findArticle).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("findAll 메소드를 호출하면 현재 저장된 Article 리스트를 리턴한다")
    void findAllTest() {
        // given
        Article article2 = new Article(2L, "ader2", "title sample2", "contents sample2", LocalDateTime.now(), LocalDateTime.now());
        Article article3 = new Article(3L, "ader3", "title sample3", "contents sample3", LocalDateTime.now(), LocalDateTime.now());
        articleJdbcRepository.save(article);
        articleJdbcRepository.save(article2);
        articleJdbcRepository.save(article3);

        // when
        List<Article> articles = articleJdbcRepository.findAll();

        // then
        assertThat(articles.size()).isEqualTo(3);
    }
}
