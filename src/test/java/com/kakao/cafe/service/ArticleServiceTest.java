package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import com.kakao.cafe.web.dto.ArticleDetailDto;
import com.kakao.cafe.web.dto.ArticleListDto;
import com.kakao.cafe.web.dto.ArticleRegisterFormDto;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleService articleService;

    Article article;

    @BeforeEach
    void setup() {
        article = new Article.ArticleBuilder()
            .setWriter("testWriter")
            .setContents("testContent")
            .setTitle("testTitle")
            .build();
    }

    @Test
    @DisplayName("게시글 등록이 아무 예외 없이 동작한다.")
    void 게시글_등록_테스트() {
        // given
        ArticleRegisterFormDto articleRegisterFormDto = new ArticleRegisterFormDto("testWriter",
            "testTitle", "testContents");

        doNothing().when(articleRepository)
            .save(any());

        // then
        assertThatCode(
            () -> articleService.register(articleRegisterFormDto)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("모든 게시글을 조회할 수 있다.")
    void 모든_게시글_조회_테스트() {
        // given
        Article article2 = new Article.ArticleBuilder()
            .setWriter("testWriter2")
            .setContents("testContent2")
            .setTitle("testTitle2")
            .build();
        List<Article> articleList = Arrays.asList(article, article2);
        given(articleRepository.findAll())
            .willReturn(articleList);

        // when
        List<ArticleListDto> resultList = articleService.showAll();

        // then
        assertThat(resultList).hasSize(2);
    }

    @Test
    @DisplayName("게시글 Id를 통해 게시글을 조회 할 수 있다.")
    void Id를_통한_게시글_조회_테스트() {
        // given
        String articleId = "1";
        given(articleRepository.findById(articleId))
            .willReturn(Optional.ofNullable(article));

        // when
        ArticleDetailDto articleDetailDto = articleService.showOne(articleId);

        // then
        assertThat(articleDetailDto.getContents()).isEqualTo(article.getContents());
        assertThat(articleDetailDto.getWriter()).isEqualTo(article.getWriter());
        assertThat(articleDetailDto.getTitle()).isEqualTo(article.getTitle());
    }

    @Test
    @DisplayName("존재하지 않는 ID의 게시글을 조회하려는 시도는 예외가 발생한다.")
    void 존재하지않는_Id의_게시글_조회_테스트() {
        // given
        String articleId = "3";
        given(articleRepository.findById(articleId))
            .willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> articleService.showOne(articleId))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("해당 게시글이 존재하지 않습니다.");
    }
}
