package com.kakao.cafe.unit.repository;

import static org.assertj.core.api.BDDAssertions.then;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.collections.ArticleCollectionRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ArticleCollectionRepository 단위 테스트")
public class ArticleCollectionRepositoryTest {

    private final ArticleCollectionRepository articleRepository = new ArticleCollectionRepository();

    Article article;

    @BeforeEach
    public void setUp() {
        article = articleRepository.save(Article.createWithInput("writer", "title", "contents"));
    }

    @Test
    @DisplayName("질문 객체를 저장소에 저장한다")
    public void saveTest() {
        // then
        then(article.getWriter()).isEqualTo("writer");
        then(article.getTitle()).isEqualTo("title");
        then(article.getContents()).isEqualTo("contents");
    }

    @Test
    @DisplayName("모든 질문 객체를 조회한다")
    public void findAllTest() {
        // when
        List<Article> articles = articleRepository.findAll();

        // then
        then(articles).containsExactly(article);
    }

    @Test
    @DisplayName("질문 id 로 질문 객체를 조회한다")
    public void findByArticleIdTest() {
        // when
        Optional<Article> findArticle = articleRepository.findById(article.getArticleId());

        // then
        then(findArticle).hasValue(article);
    }

}
