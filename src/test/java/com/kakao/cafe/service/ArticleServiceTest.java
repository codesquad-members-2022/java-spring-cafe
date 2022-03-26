package com.kakao.cafe.service;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.article.ArticleRepository;
import com.kakao.cafe.exception.ClientException;
import com.kakao.cafe.web.dto.ArticleDto;
import com.kakao.cafe.web.dto.ArticleResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    private Article article;

    @BeforeEach
    void setUp() {
        article = Article.of(1, "writer", "title", "contents", LocalDateTime.of(2022,03,11,11,25));
    }

    @Test
    @DisplayName("게시글을 작성하면 저장소에 게시물이 저장된다.")
    void writeTest() {

        given(articleRepository.save(any())).willReturn(article);
        String writer = "userName";
        Article actual = articleService.write(writer, new ArticleDto("title", "contents"));

        assertThat(actual).isEqualTo(article);
        assertThat(actual.getTitle()).isEqualTo("title");
        assertThat(actual.getWriter()).isEqualTo("writer");
        assertThat(actual.getContents()).isEqualTo("contents");
        assertThat(actual.getWrittenTime()).isEqualTo(LocalDateTime.of(2022,03,11,11,25));
    }

    @Test
    @DisplayName("게시물을 id로 조회할 수 있다.")
    void findOneTest() {
        int id = 1;
        given(articleRepository.findById(id)).willReturn(Optional.of(article));

        ArticleResponseDto articleResponseDto = articleService.findOne(id);

        assertThat(articleResponseDto.getId()).isEqualTo(article.getId());
        assertThat(articleResponseDto.getWriter()).isEqualTo(article.getWriter());
        assertThat(articleResponseDto.getTitle()).isEqualTo(article.getTitle());
        assertThat(articleResponseDto.getContents()).isEqualTo(article.getContents());
        assertThat(articleResponseDto.getWrittenTime()).isEqualTo(article.getWrittenTime());

    }

    @ParameterizedTest
    @DisplayName("찾는 게시글이 없으면 ClientException을 발생시킨다.")
    @ValueSource(ints = {-1,0})
    void findOneTest_error(int id) {

        given(articleRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() -> articleService.findOne(id))
                .isInstanceOf(ClientException.class)
                .hasMessage("게시글을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("모든 게시글을 조회하면 ArticleResponseDto로 변환해서 반환한다.")
    void findAllTest() {

        given(articleRepository.findAll()).willReturn(List.of(article));

        assertThat(articleService.findAll()).contains(new ArticleResponseDto(article));
    }
}
