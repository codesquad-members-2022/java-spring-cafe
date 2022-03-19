package com.kakao.cafe.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakao.cafe.domain.Article;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
class JdbcTemplateArticleRepositoryTest {

    ArticleRepository articleRepository;

    @Autowired
    public JdbcTemplateArticleRepositoryTest(DataSource dataSource) {
        articleRepository = new JdbcTemplateArticleRepository(dataSource);
    }

    @Test
    @DisplayName("게시글 번호를 통해 게시글을 검색할 수 있다.")
    void 게시글_조회_테스트() {
        // given
        String articleId = "1";

        // when
        Article resultArticle = articleRepository.findById(articleId).get();

        // then
        assertThat(resultArticle.getTitle()).isEqualTo("title1");
        assertThat(resultArticle.getWriter()).isEqualTo("writer1");
        assertThat(resultArticle.getContents()).isEqualTo("content1");
    }

    @Test
    @DisplayName("신규 게시글을 등록할 수 있다.")
    void 게시글_등록_테스트() {
        // given
        Article newArticle = new Article.ArticleBuilder()
            .setWriter("writer3")
            .setTitle("title3")
            .setContents("content3")
            .build();

        // when
        articleRepository.save(newArticle);

        // then
        Article resultArticle = articleRepository.findById("3").get();
        assertThat(resultArticle.getTitle()).isEqualTo("title3");
        assertThat(resultArticle.getWriter()).isEqualTo("writer3");
        assertThat(resultArticle.getContents()).isEqualTo("content3");
    }

    @Test
    @DisplayName("모든 게시글을 내림차순으로 조회할 수 있다.")
    void 모든_게시글_조회_테스트() {
        // when
        List<Article> resultArticleList = articleRepository.findAll();

        // then
        assertThat(resultArticleList).hasSize(2);

        assertThat(resultArticleList.get(1).getTitle()).isEqualTo("title1");
        assertThat(resultArticleList.get(1).getWriter()).isEqualTo("writer1");
        assertThat(resultArticleList.get(1).getContents()).isEqualTo("content1");

        assertThat(resultArticleList.get(0).getTitle()).isEqualTo("title2");
        assertThat(resultArticleList.get(0).getWriter()).isEqualTo("writer2");
        assertThat(resultArticleList.get(0).getContents()).isEqualTo("content2");
    }
}
