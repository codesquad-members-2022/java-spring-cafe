package com.kakao.cafe.repository;

import com.kakao.cafe.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class JdbcTemplateArticleRepositorsyTest {

    @Autowired DataSource dataSource;
    private ArticleRepository repository;

    @BeforeEach
    void setUp() {
        repository = new JdbcTemplateArticleRepository(dataSource);
    }

    @Test
    void save메소드는_만약_article_객체가_주어진다면_article_객체를_저장한다() {
        Article article = new Article("쿠킴", "제목1234", "본문1234");

        Article result = repository.save(article);

        assertThat(result.getWriter()).isEqualTo("쿠킴");
        assertThat(result.getTitle()).isEqualTo("제목1234");
        assertThat(result.getContents()).isEqualTo("본문1234");
    }

    @Test
    void findAll메소드는_만약_파라미터없이_실행된다면_저장되어있는_article_모두를_리스트로_리턴한다() {
        Article article1 = repository.save(new Article("쿠킴1", "제목1", "본문1"));
        Article article2 = repository.save(new Article("쿠킴2", "제목1234", "본문1234"));

        List<Article> result = repository.findAll();

        assertThat(result).contains(article1, article2);
    }

    @Test
    void findByArticleId메소드는_만약_존재하는_id가주어진다면_해당id의_옵셔널_Article를_리턴한다() {
        Article article = repository.save(new Article("쿠킴1", "제목1", "본문1"));

        Optional<Article> result = repository.findByArticleId(article.getId());

        assertThat(result.get()).isEqualTo(article);
    }

    @Test
    void findByArticleId메소드는_만약_존재하지않는_id가주어진다면_empty옵셔널을_리턴한다() {
        Optional<Article> result = repository.findByArticleId(-1);

        assertThat(result.isEmpty()).isTrue();
    }
}
