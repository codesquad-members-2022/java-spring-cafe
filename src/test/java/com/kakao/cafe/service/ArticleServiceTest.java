package com.kakao.cafe.service;

import com.kakao.cafe.controller.dto.ArticleSaveRequestDto;
import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.article.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleService articleService;

    ArticleSaveRequestDto dto;

    @BeforeEach
    void setUp() {
        String writer = "user";
        String title = "title";
        String contents = "contents";

        dto = new ArticleSaveRequestDto(writer, title, contents);
    }

    @Test
    @DisplayName("게시글이 저장된다")
    void articleSaveTest() {
        // given
        doNothing().when(articleRepository).save(any());

        // then
        assertThatCode(() -> articleService.save(dto)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("게시글 목록이 조회된다")
    void articleListTest() {
        // given
        String writer = "user2";
        String title = "title2";
        String contents = "contents2";

        ArticleSaveRequestDto dto2 = new ArticleSaveRequestDto(writer, title, contents);

        given(articleRepository.findAllDesc()).willReturn(List.of(dto.toEntity(), dto2.toEntity()));

        // when
        List<Article> articleList = articleService.findAllDesc();

        // then
        assertThat(articleList).hasSize(2);
        assertThat(articleList.get(0).getTitle()).isEqualTo(dto.getTitle());
        assertThat(articleList.get(1).getTitle()).isEqualTo(dto2.getTitle());
    }

    @Test
    @DisplayName("게시글 아이디로 조회가 된다")
    void articleFindByIdTest() {
        // given
        Article article = dto.toEntity();
        Long id = 0L;
        article.setId(id);
        article.setCreatedDate(new Date().toString());

        given(articleRepository.findById(id)).willReturn(Optional.of(article));

        // when
        Article byId = articleService.findById(id);

        // then
        assertThat(byId.getTitle()).isEqualTo(article.getTitle());
        assertThat(byId.getId()).isEqualTo(id);
    }
}
