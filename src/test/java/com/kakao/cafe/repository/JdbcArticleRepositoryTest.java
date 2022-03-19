package com.kakao.cafe.repository;

import com.kakao.cafe.config.QueryLoader;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.db.DbArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

@JdbcTest
@Import(QueryLoader.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JdbcArticleRepositoryTest {
    private final DbArticleRepository repository;

    @Autowired
    public JdbcArticleRepositoryTest(DataSource dataSource, QueryLoader loader) {
        this.repository = new DbArticleRepository(dataSource, loader);
    }

    Article article;

    @BeforeEach
    public void setUp() {
        article = new Article("Shine", "title", "something...", LocalDateTime.now());
    }

    @Test
    public void saveTest() {
        // when
        Long saveId = repository.save(article);
        Optional<Article> findArticle = repository.findById(saveId);

        // then
        then(findArticle).hasValueSatisfying(article -> {
            then(article.getUserId()).isEqualTo("Shine");
            then(article.getTitle()).isEqualTo("title");
            then(article.getContents()).isEqualTo("something...");
        });
    }

    @Test
    public void findByArticleIdTest() {
        // given
        Long saveId = repository.save(article);

        // when
        Article findArticle = repository.findById(saveId).get();

        // then
        Assertions.assertThat(article).isEqualTo(findArticle);
        //then(findArticle).isEqualTo(article);
    }

    @Test
    public void findNullTest() {
        // when
        Optional<Article> findArticle = repository.findById(1000L);

        // then
        then(findArticle).isEqualTo(Optional.empty());
    }

    @Test
    public void findArticlesTest() {
        // given
        Article article2 = new Article("Shine2", "title2", "something...2", LocalDateTime.now());
        repository.save(article);
        repository.save(article2);

        // when
        List<Article> findArticles = repository.findAll();

        // then
        then(findArticles).contains(article, article2);
    }
}
