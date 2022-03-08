package com.kakao.cafe.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.jdbc.ArticleJdbcRepository;
import com.kakao.cafe.repository.jdbc.GeneratedKeyHolderFactory;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("classpath:/sql/table.sql")
@Import({ArticleJdbcRepository.class, GeneratedKeyHolderFactory.class, QueryProps.class})
public class ArticleJdbcRepositoryTest {

    @Autowired
    private ArticleJdbcRepository articleRepository;

    Article article;

    @BeforeEach
    public void setUp() {
        article = new Article.Builder()
            .writer("writer")
            .title("title")
            .contents("contents")
            .build();
    }

    @Test
    @DisplayName("질문 객체를 저장소에 저장한다")
    public void saveTest() {
        // when
        Article savedArticle = articleRepository.save(article);

        // then
        assertThat(savedArticle.getArticleId()).isEqualTo(1);
        assertThat(savedArticle.getWriter()).isEqualTo("writer");
        assertThat(savedArticle.getTitle()).isEqualTo("title");
        assertThat(savedArticle.getContents()).isEqualTo("contents");
    }

    @Test
    @DisplayName("모든 질문 객체를 조회한다")
    public void findAllTest() {
        // given
        Article savedArticle = articleRepository.save(article);

        // when
        List<Article> findArticles = articleRepository.findAll();

        // then
        assertThat(findArticles).containsExactlyElementsOf(List.of(savedArticle));
    }

    @Test
    @DisplayName("질문 id 로 질문 객체를 조회한다")
    public void findByArticleIdTest() {
        // given
        Article savedArticle = articleRepository.save(article);

        // when
        Optional<Article> findArticle = articleRepository.findById(savedArticle.getArticleId());

        // then
        assertThat(findArticle).hasValue(savedArticle);
    }

    @Test
    @DisplayName("존재하지 않는 질문 id 로 질문 객체를 조회한다")
    public void findNullTest() {
        // given
        Article savedArticle = articleRepository.save(article);

        // when
        Optional<Article> findArticle = articleRepository.findById(savedArticle.getArticleId() + 1);

        // then
        assertThat(findArticle).isEqualTo(Optional.empty());
    }

}

