package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
// @JdbcTest
// @Sql("classpath:sql/schema.sql")
class JdbcTemplateArticleRepositoryTest {

    ArticleRepository articleRepository;
    @Autowired DataSource dataSource;

    @BeforeEach
    void init() {
        articleRepository = new JdbcTemplateArticleRepository(dataSource);
    }

    @Test
    @DisplayName("게시글 저장이 잘 되는가?")
    void saveTest(){
        Article article = new Article("게시글1", "나단", "안녕하세욧~~ 안녕하세욧~~ 안녕하세욧~~ 안녕하세욧~~ 안녕하세욧~~");
        Article savedArticle = articleRepository.save(article);
        System.out.println(savedArticle.getIndex());
        assertThat("게시글1").isEqualTo(savedArticle.getTitle());
    }

    @Test
    @DisplayName("전체 게시글 불러오기가 잘 되는가?")
    void findAllTest() {
        List<Article> articles = articleRepository.findAll();
        assertThat("게시글제목1").isEqualTo(articles.get(0).getTitle());
        assertThat("게시글제목2").isEqualTo(articles.get(1).getTitle());
        assertThat("게시글제목3").isEqualTo(articles.get(2).getTitle());
        }
}

