package com.kakao.cafe.article.domain;

import com.kakao.cafe.article.controller.dto.ArticleWriteRequest;
import com.kakao.cafe.exception.domain.RequiredFieldNotFoundException;
import com.kakao.cafe.utils.DateUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Article 단위 테스트")
class ArticleTest {

    @Nested
    @DisplayName("새로운 글을 작성할 때")
    class CreateTest {

        private static final String RequiredFieldNotFound = "필수 정보가 없습니다.";

        @Nested
        @DisplayName("글 작성 요청으로 생성할 경우")
        class WithWriteRequestTest{

            @Test
            void 제목이_없으면_오류가_발생한다() {
                // arrange
                ArticleWriteRequest writeRequest = new ArticleWriteRequest();
                writeRequest.setContent("내용입니다");

                // assert
                assertThatThrownBy(() -> Article.createWithWriteRequest(writeRequest))
                        .isInstanceOf(RequiredFieldNotFoundException.class)
                        .hasMessageContaining(RequiredFieldNotFound);
            }
        }
    }
}