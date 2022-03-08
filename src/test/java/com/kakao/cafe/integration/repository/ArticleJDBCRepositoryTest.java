package com.kakao.cafe.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.jdbc.ArticleJDBCRepository;
import com.kakao.cafe.repository.jdbc.GeneratedKeyHolderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("classpath:/sql/table.sql")
@Import({ArticleJDBCRepository.class, GeneratedKeyHolderFactory.class, QueryProps.class})
public class ArticleJDBCRepositoryTest {

    @Autowired
    private ArticleJDBCRepository articleRepository;

    Article article;

    @BeforeEach
    public void setUp() {
        article = new Article.Builder()
            .articleId(1)
            .writer("writer")
            .title("title")
            .contents("contents")
            .build();
    }

    @Test
    public void test() {
        // when
        Article savedArticle = articleRepository.save(article);

        // then
        assertThat(savedArticle.getArticleId()).isEqualTo(1);
        assertThat(savedArticle.getWriter()).isEqualTo("writer");
        assertThat(savedArticle.getTitle()).isEqualTo("title");
        assertThat(savedArticle.getContents()).isEqualTo("contents");
    }
}
