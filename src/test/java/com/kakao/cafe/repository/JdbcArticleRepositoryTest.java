package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;

@DisplayName("JdbcArticleRepository 단위 테스트")
@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class JdbcArticleRepositoryTest {

    private final JdbcArticleRepository jdbcArticleRepository;
    private final JdbcUserRepository jdbcUserRepository;

    @Autowired
    public JdbcArticleRepositoryTest(DataSource dataSource) {
        jdbcArticleRepository = new JdbcArticleRepository(dataSource);
        jdbcUserRepository = new JdbcUserRepository(dataSource);
    }

    private Article article;

    @BeforeEach
    void setup() {
        article = new Article(1, "ikjo", "조명익","java", "java is fun", LocalDateTime.now());
        jdbcUserRepository.save(new User("ikjo", "1234", "조명익", "auddlr100@naver.com"));
        jdbcArticleRepository.deleteAll();
    }

    @DisplayName("주어진 article 객체의 게시글 정보 데이터를 저장한다.")
    @Test
    void 게시글_정보_저장() {
        // when
        jdbcArticleRepository.save(article);

        // then
        Article result = jdbcArticleRepository.findById(1).orElseThrow(() -> new NoSuchElementException("해당하는 글이 없습니다."));
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getUserId()).isEqualTo("ikjo");
        assertThat(result.getWriter()).isEqualTo("조명익");
        assertThat(result.getTitle()).isEqualTo("java");
        assertThat(result.getContents()).isEqualTo("java is fun");
    }

    @DisplayName("주어진 article 객체의 게시글 정보 데이터를 수정한다.")
    @Test
    void 게시글_정보_수정() {
        // given
        jdbcArticleRepository.save(article);

        Article otherArticle = new Article(1, "ikjo", "조명익","python", "python is fun", LocalDateTime.now());

        // when
        jdbcArticleRepository.save(otherArticle);

        // then
        Article result = jdbcArticleRepository.findById(1).orElseThrow(() -> new NoSuchElementException("해당하는 글이 없습니다."));
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getUserId()).isEqualTo("ikjo");
        assertThat(result.getWriter()).isEqualTo("조명익");
        assertThat(result.getTitle()).isEqualTo("python");
        assertThat(result.getContents()).isEqualTo("python is fun");
    }

    @DisplayName("게시글 ID로 해당 게시글 정보 데이터를 조회한다.")
    @Test
    void 특정_게시글_정보_조회() {
        // given
        jdbcArticleRepository.save(article);

        // when
        Article result = jdbcArticleRepository.findById(1).orElseThrow(() -> new NoSuchElementException("해당하는 글이 없습니다."));

        // then
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getUserId()).isEqualTo("ikjo");
        assertThat(result.getWriter()).isEqualTo("조명익");
        assertThat(result.getTitle()).isEqualTo("java");
        assertThat(result.getContents()).isEqualTo("java is fun");
    }

    @DisplayName("저장된 게시글 정보 2개를 모두 조회한다.")
    @Test
    void 모든_게시글_정보_조회() {
        // given
        jdbcArticleRepository.save(article);
        jdbcArticleRepository.save(new Article(2, "ikjo", "조명익","python", "python is fun", LocalDateTime.now()));

        // when
        List<Article> result = jdbcArticleRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @DisplayName("입력받은 게시글 id를 통해 게시글 정보 데이터를 삭제한다.")
    @Test
    void 게시글_정보_삭제() {
        // given
        jdbcArticleRepository.save(article);

        // when
        jdbcArticleRepository.deleteById(1);

        // when, then
        assertThat(jdbcArticleRepository.findAll().size()).isEqualTo(0);
    }
}
