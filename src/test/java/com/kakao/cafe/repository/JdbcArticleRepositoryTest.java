package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql(scripts = {"classpath:tables.sql"})
class JdbcArticleRepositoryTest {

    @Autowired
    DataSource dataSource;
    JdbcArticleRepository articleRepository;

    @BeforeEach
    void beforeEach() {
        articleRepository = new JdbcArticleRepository(dataSource);
    }

    @Test
    @DisplayName("article을 생성하고 save했을 때 findById로 찾은 article의 멤버변수의 값이 일치하면 true를 반환해야 한다")
    void save() {
        //given
        Article article = new Article("asdf", "title", "content123");
        article.setArticleId(1);
        //when
        articleRepository.save(article);
        //then
        assertThat(articleRepository.findById(1).isEqualArticle(article)).isEqualTo(true);
    }

    @Test
    @DisplayName("article을 생성하고 save했을 때 getArticles로 찾은 배열의 크기가 저장한 갯수와 일치해야 한다")
    void getArticleList() {
        //given
        Article article = new Article("asdf", "title", "content123");
        article.setArticleId(1);
        //when
        articleRepository.save(article);
        //then
        assertThat(articleRepository.getArticles().size()).isEqualTo(1);
    }

}
