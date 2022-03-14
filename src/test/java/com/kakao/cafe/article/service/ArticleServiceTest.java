package com.kakao.cafe.article.service;

import com.kakao.cafe.article.controller.dto.ArticleWriteRequest;
import com.kakao.cafe.article.domain.Article;
import com.kakao.cafe.article.repository.ArticleRepository;
import com.kakao.cafe.article.repository.MemoryArticleRepository;
import com.kakao.cafe.exception.domain.RequiredFieldNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("ArticleService 통합 테스트")
class ArticleServiceTest {

    private final ArticleRepository articleRepository = new MemoryArticleRepository();
    private final ArticleService articleService = new ArticleService(articleRepository);

    @Nested
    @DisplayName("새로운 글을 저장할 때")
    class WriteTest{

        @Nested
        @DisplayName("모든 정보가 정상적으로 들어오면")
        class CorrectDataTest {

            @Test
            void 글_등록에_성공한다() {
                // arrange
                ArticleWriteRequest writeRequest = new ArticleWriteRequest();
                writeRequest.setTitle("제목입니다.");
                writeRequest.setContent("내용입니다.");

                // act
                Article savedArticle = articleService.write(writeRequest);

                // assert
                assertThat(savedArticle).isNotNull();
                assertThat(savedArticle.equalsTitle(writeRequest.getTitle())).isTrue();
                assertThat(savedArticle.equalsContent(writeRequest.getContent())).isTrue();
            }
        }

        @Nested
        @DisplayName("제목이 없으면")
        class TitleEmptyTest {

            private static final String RequiredFieldNotFoundExceptionMessage = "필수 정보가 없습니다.";

            @Test
            void 예외가_발생한다() {
                // arrange
                ArticleWriteRequest writeRequest = new ArticleWriteRequest();
                writeRequest.setContent("제목입니다.");

                // assert
                assertThatThrownBy(()-> articleService.write(writeRequest))
                        .isInstanceOf(RequiredFieldNotFoundException.class)
                        .hasMessageContaining(RequiredFieldNotFoundExceptionMessage);
            }
        }
    }

}