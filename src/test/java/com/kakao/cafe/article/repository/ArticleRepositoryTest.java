package com.kakao.cafe.article.repository;

import com.kakao.cafe.article.domain.Article;
import com.kakao.cafe.article.exception.ArticleNotFoundException;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("ArticleRepository 단위 테스트")
class ArticleRepositoryTest {

    private final ArticleRepository articleRepository = new MemoryArticleRepository();

    @BeforeEach
    void setup() {
        articleRepository.deleteAll();
    }

    @Nested
    @DisplayName("글을 저장할 때")
    class SaveTest{

        @Nested
        @DisplayName("등록 요청이면")
        class CreateTest {

            @Test
            void 글이_새로_저장된다() {
                // arrange
                Article expectedArticle = getIdNullArticle();

                // act
                Article savedArticle = articleRepository.save(expectedArticle).orElseThrow();
                expectedArticle.setId(savedArticle.getId());

                // assert
                assertThat(savedArticle).isEqualTo(expectedArticle);
            }
        }

        @Nested
        @DisplayName("수정 요청이고")
        class ModifyTest {

            @Test
            void 저장된_글이면_수정이하고_수정된_글을_반환한다() {
                // arrange
                Article modifiedArticle = articleRepository.save(getIdNullArticle()).orElseThrow();
                modifiedArticle.addViewCount();
                LocalDateTime beforeUpdatedModifiedDate = modifiedArticle.getModifiedDate();

                // act
                Article updatedArticle = articleRepository.save(modifiedArticle).orElseThrow();

                // assert
                assertThat(updatedArticle.equalsId(modifiedArticle.getId())).isTrue();
                assertThat(updatedArticle.equalsViewCount(modifiedArticle.getViewCount())).isTrue();
                assertThat(updatedArticle.getModifiedDate()).isAfter(beforeUpdatedModifiedDate);
            }

            @Test
            void 저장되지_않은_글이면_수정되지_않고_글도_반환되지_않는다() {
                // arrange
                Article unsavedArticle = getArticle();

                // assert
                articleRepository.save(unsavedArticle)
                        .ifPresent(article -> fail());
            }
        }
    }

    @Nested
    @DisplayName("글의 상세 정보를 검색할 때")
    class FindByIdTest {

        @Nested
        @DisplayName("저장된 id 로 검색하면")
        class FindBySavedIdTest {

            @Test
            void 정상적으로_글을_반환한다() {
                // arrange
                Article savedArticle = articleRepository.save(getIdNullArticle()).orElseThrow();

                // act
                Article findArticle = articleRepository.findById(savedArticle.getId()).orElseThrow();

                // assert
                assertThat(findArticle).isEqualTo(savedArticle);
            }
        }

        @Nested
        @DisplayName("저장되지 않은 글id 로 검색하면")
        class FindByUnsavedIdTest {
            @Test
            void 글을_찾을수없다() {
                // arrange
                int unsavedId = 1;

                // assert
                articleRepository.findById(unsavedId)
                        .ifPresent(article -> fail());

            }
        }
    }

    @Nested
    @DisplayName("글 목록을 조회할 때")
    class FindAllTest{
        @Nested
        @DisplayName("저장된 글이 있으면")
        class ArticleExistTest {
            @Test
            void 글_목록을_반환한다() {
                // arrange
                Article savedArticle = articleRepository.save(getIdNullArticle()).orElseThrow();

                // act
                List<Article> articles = articleRepository.findAll().orElseThrow();

                // assert
                assertThat(articles).size().isEqualTo(1);
                assertThat(articles.get(0)).isEqualTo(savedArticle);
            }
        }

        @Nested
        @DisplayName("저장된 글이 없으면")
        class NoArticleTest {
            @Test
            void 빈_목록을_반환한다() {
                // act
                List<Article> articles = articleRepository.findAll().orElseThrow();

                // assert
                assertThat(articles).size().isEqualTo(0);
            }
        }
    }

    private Article getIdNullArticle() {
        LocalDateTime datetime = LocalDateTime.MIN;
        return new Article("제목 입니다.","내용 입니다.", datetime, datetime);
    }

    private Article getArticle() {
        Article article = getIdNullArticle();
        article.setId(1);

        return article;
    }

}