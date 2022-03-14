package com.kakao.cafe.article.repository;

import com.kakao.cafe.article.domain.Article;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ArticleRepository 단위 테스트")
class ArticleRepositoryTest {

    private final ArticleRepository articleRepository = new MemoryArticleRepository();

    @BeforeEach
    void setup() {
        articleRepository.deleteAll();
    }

    @Nested
    @DisplayName("새로운 글을 저장할 때")
    class SaveTest{

        @Nested
        @DisplayName("정보가 정상적으로 들어왔을 경우")
        class CorrectDataTest {

            @Test
            void 등록에_성공한다() {
                // arrange
                Article expectedArticle = getArticle();

                // act
                Article savedArticle = articleRepository.save(expectedArticle).orElseThrow();
                expectedArticle.setId(savedArticle.getId());

                // assert
                assertThat(savedArticle).isEqualTo(expectedArticle);
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
                Article expectedArticle = getArticle();
                Article savedArticle = articleRepository.save(expectedArticle).orElseThrow();
                expectedArticle.setId(savedArticle.getId());

                // act
                Article findArticle = articleRepository.findById(expectedArticle.getId()).orElseThrow();

                // assert
                assertThat(findArticle).isEqualTo(expectedArticle);
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
                        .ifPresent(article -> Assertions.fail());

            }
        }
    }

    private Article getArticle() {
        String expectedTitle = "제목 입니다.";
        String expectedContent = "내용 입니다.";
        LocalDateTime expectedCreatedDate = LocalDateTime.now();
        LocalDateTime expectedModifiedDate = LocalDateTime.now();

        return new Article(expectedTitle,
                expectedContent,
                expectedCreatedDate,
                expectedModifiedDate);
    }

}