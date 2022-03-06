package com.kakao.cafe.service;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.article.MemoryArticleRepository;
import com.kakao.cafe.exception.ClientException;
import com.kakao.cafe.web.dto.ArticleDto;
import com.kakao.cafe.web.dto.ArticleResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ArticleServiceTest {

    private ArticleService articleService;
    private Article article;

    @BeforeEach
    void setUp() {
        articleService = new ArticleService(new MemoryArticleRepository());
        article = new Article("작성자", "제목", "본문");
    }

    @Test
    @DisplayName("게시글을 작성하면 저장소에 게시물이 저장된다.")
    void writeTest() {

        assertThat(articleService.findAll().size()).isEqualTo(0);

        articleService.write(article);

        assertThat(articleService.findAll().size()).isEqualTo(1);

    }

    @Test
    @DisplayName("게시물을 id로 조회할 수 있다.")
    void findOneTest() {
        articleService.write(article);

        assertThat(articleService.findOne(1).getWriter()).isEqualTo("작성자");
        assertThat(articleService.findOne(1).getTitle()).isEqualTo("제목");
        assertThat(articleService.findOne(1).getContents()).isEqualTo("본문");
        assertThat(articleService.findOne(1)).isEqualTo(new ArticleResponseDto(article));

    }

    @ParameterizedTest
    @DisplayName("찾는 게시글이 없으면 ClientException을 발생시킨다.")
    @ValueSource(ints = {-1,0,2})
    void findOneTest_error(int id) {
        articleService.write(article);

        assertThatThrownBy(()->articleService.findOne(id))
                .isInstanceOf(ClientException.class)
                .hasMessage("게시글을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("모든 게시글을 조회하면 ArticleDto로 변환해서 반환한다.")
    void findAllTest() {
        articleService.write(article);

        assertThat(articleService.findAll()).contains(new ArticleResponseDto(article));
    }
}
