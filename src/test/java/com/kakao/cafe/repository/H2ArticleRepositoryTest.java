package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.*;

import com.kakao.cafe.domain.Article;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
class H2ArticleRepositoryTest {

    H2ArticleRepository h2ArticleRepository;

    @Autowired
    public H2ArticleRepositoryTest(DataSource dataSource) {
        this.h2ArticleRepository = new H2ArticleRepository(dataSource);
    }

    @Test
    @DisplayName("게시글을 저장하고, 저장된 게시글의 id를 리턴한다.")
    void save() {
        //given
        Article validArticle = new Article("Anonymous", "test title", "test content");

        //when
        int savedArticleCount = h2ArticleRepository.save(validArticle);

        //then
        assertThat(savedArticleCount).isEqualTo(4);
    }

    @Test
    @DisplayName("게시글을 존재하는 id로 조회하면, 해당 id를 가진 게시글을 리턴한다.")
    void findById_validId() {
        //given

        //when
        Optional<Article> result = h2ArticleRepository.findById(1);

        //then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().hasSameId(1)).isTrue();
    }

    @Test
    @DisplayName("게시글을 존재하지 않는 id로 조회하면, 결과를 반환하지 않는다.")
    void findById_invalidId() {
        //given

        //when
        Optional<Article> result = h2ArticleRepository.findById(0);

        //then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("게시글을 전부 조회하면, 저장된 게시글 전부를 List에 담아 리턴한다.")
    void findAll() {
        //given

        //when
        List<Article> articles = h2ArticleRepository.findAll();

        //then
        assertThat(articles.size()).isEqualTo(3);
    }
}
