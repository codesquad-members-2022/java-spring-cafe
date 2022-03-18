package com.kakao.cafe.integration.repository;


import static org.assertj.core.api.BDDAssertions.then;

import com.kakao.cafe.config.QueryProps;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.jdbc.ArticleJdbcRepository;
import com.kakao.cafe.repository.jdbc.GeneratedKeyHolderFactory;
import com.kakao.cafe.repository.jdbc.KeyHolderFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@ActiveProfiles(profiles = "local")
@Sql("classpath:/schema-h2.sql")
@Import({GeneratedKeyHolderFactory.class, QueryProps.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DisplayName("ArticleJdbcRepository JDBC 통합 테스트")
public class ArticleJdbcRepositoryTest {

    private final ArticleJdbcRepository articleRepository;
    private Article article;

    @Autowired
    public ArticleJdbcRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate,
        KeyHolderFactory keyHolderFactory, QueryProps queryProps) {
        this.articleRepository = new ArticleJdbcRepository(jdbcTemplate, keyHolderFactory,
            queryProps);
    }

    @BeforeEach
    public void setUp() {
        article = new Article("writer", "title", "contents");

    }

    @Test
    @DisplayName("질문 객체를 저장소에 저장한다")
    public void saveTest() {
        // when
        Article savedArticle = articleRepository.save(article);
        Optional<Article> findArticle = articleRepository.findById(savedArticle.getArticleId());

        // then
        then(findArticle)
            .hasValueSatisfying(article -> {
                then(article.getWriter()).isEqualTo("writer");
                then(article.getTitle()).isEqualTo("title");
                then(article.getContents()).isEqualTo("contents");
            });
    }

    @Test
    @DisplayName("모든 질문 객체를 조회한다")
    public void findAllTest() {
        // given
        Article savedArticle = articleRepository.save(article);

        // when
        List<Article> findArticles = articleRepository.findAll();

        // then
        then(findArticles).containsExactlyElementsOf(List.of(savedArticle));
    }

    @Test
    @DisplayName("질문 id 로 질문 객체를 조회한다")
    public void findByArticleIdTest() {
        // given
        Article savedArticle = articleRepository.save(article);

        // when
        Optional<Article> findArticle = articleRepository.findById(savedArticle.getArticleId());

        // then
        then(findArticle).hasValue(savedArticle);
    }

    @Test
    @DisplayName("존재하지 않는 질문 id 로 질문 객체를 조회한다")
    public void findNullTest() {
        // when
        Optional<Article> findArticle = articleRepository.findById(article.getArticleId());

        // then
        then(findArticle).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("질문 id 를 포함한 질문 객체를 저장해 업데이트한다")
    public void saveMergeTest() {
        // given
        articleRepository.save(article);

        Article changedArticle = new Article(1, "writer", "otherTitle", "otherContents",
            LocalDateTime.now());

        // when
        articleRepository.save(changedArticle);
        Optional<Article> findArticle = articleRepository.findById(changedArticle.getArticleId());

        // then
        then(findArticle)
            .hasValueSatisfying(article -> {
                then(article.getArticleId()).isEqualTo(1);
                then(article.getWriter()).isEqualTo("writer");
                then(article.getTitle()).isEqualTo("otherTitle");
                then(article.getContents()).isEqualTo("otherContents");
            });
    }

    @Test
    @DisplayName("질문 id 로 질문 객체를 삭제한다")
    public void deleteByArticleIdTest() {
        // given
        articleRepository.save(article);

        // when
        articleRepository.deleteById(article.getArticleId());
        Optional<Article> findArticle = articleRepository.findById(article.getArticleId());

        // then
        then(findArticle).isEqualTo(Optional.empty());

    }

}

