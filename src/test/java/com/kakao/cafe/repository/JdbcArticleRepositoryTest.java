package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql(scripts = {"classpath:schema.sql"})
class JdbcArticleRepositoryTest {

    @Autowired
    DataSource dataSource;

    JdbcArticleRepository repository;

    @BeforeEach
    void beforeEach() {
        repository = new JdbcArticleRepository(dataSource);
    }

    @Test
    @DisplayName("전체 게시글 목록을 반환한다.")
    void findAllSuccess() {
        // given
        LocalDate currentDate = LocalDate.now();

        Article article1 = new Article(1, "writer", "title", "contents", currentDate);
        repository.save(article1);

        Article article2 = new Article(2, "writer", "title", "contents", currentDate);
        repository.save(article2);

        // when
        List<Article> articles = repository.findAll();

        // then
        assertThat(articles.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("인자로 주어진 게시글을 저장소에 저장한다.")
    void persistSuccess() {
        // given
        LocalDate currentDate = LocalDate.now();

        Article article = new Article(1, "writer", "title", "contents", currentDate);

        // when
        repository.save(article);

        // then
        Article result = repository.findById(article.getId()).get();
        assertThat(result).usingRecursiveComparison().isEqualTo(article);
    }

    @Test
    @DisplayName("인자로 주어진 게시글을 저장소에 업데이트한다.")
    void mergeSuccess() {
        // given
        LocalDate currentDate = LocalDate.now();

        Article article = new Article(1, "user", "1234", "name", currentDate);
        repository.save(article);

        // when
        Article modifiedArticle = new Article(1, "user", "1234", "name", currentDate);
        repository.save(modifiedArticle);

        // then
        Article result = repository.findById(modifiedArticle.getId()).get();
        assertThat(result).usingRecursiveComparison().isEqualTo(modifiedArticle);
    }

    @Test
    @DisplayName("인자로 주어진 ID를 가진 게시글을 저장소에서 찾아 반환한다.")
    void findByIdSuccess() {
        // given
        int id = 1;
        LocalDate currentDate = LocalDate.now();

        Article article = new Article(id, "writer", "title", "contents", currentDate);
        repository.save(article);

        // when
        Article result = repository.findById(id).get();

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(article);
    }

    @Test
    @DisplayName("인자로 주어진 ID를 가진 게시글을 저장소에서 찾아 삭제하고 삭제한 게시글의 개수를 반환한다.")
    void deleteByIdSuccess() {
        // given
        int id = 1;
        LocalDate currentDate = LocalDate.now();

        Article article = new Article(id, "writer", "title", "contents", currentDate);
        repository.save(article);

        // when
        int resultCount = repository.deleteById(id);

        // then
        assertThat(resultCount).isNotEqualTo(0);
    }
}
