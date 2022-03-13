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
@Sql(scripts = {"classpath:h2_ddl.sql"})
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
    void findAll() {
        //given
        Article article1 = new Article(1, "wrtier", "title", "contents", LocalDate.now());
        repository.save(article1);

        Article article2 = new Article(2, "wrtier", "title", "contents", LocalDate.now());
        repository.save(article2);

        //when
        List<Article> articles = repository.findAll();

        //then
        assertThat(articles.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("인자로 주어진 게시글을 저장소에 저장한다.")
    void persist() {
        //given
        Article article = new Article(1, "wrtier", "title", "contents", LocalDate.now());

        //when
        repository.save(article);

        //then
        Article result = repository.findOne(article.getId()).get();
        assertThat(result).isEqualTo(article);
    }

    @Test
    @DisplayName("인자로 주어진 ID를 가진 게시글을 저장소에서 찾아 반환한다.")
    void findOne() {
        //given
        Article article = new Article(1, "wrtier", "title", "contents", LocalDate.now());
        repository.save(article);

        //when
        Article result = repository.findOne(article.getId()).get();

        //then
        assertThat(result).isEqualTo(article);
    }
}
