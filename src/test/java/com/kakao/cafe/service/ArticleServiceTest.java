package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.*;

import com.kakao.cafe.controller.dto.PostingRequestDto;
import com.kakao.cafe.repository.MemoryArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArticleServiceTest {

    private final MemoryArticleRepository articleRepository = new MemoryArticleRepository();
    private final ArticleService articleService = new ArticleService(articleRepository);

    @BeforeEach
    void setUp() {
        articleRepository.clear();
    }

    @Test
    void posting() {
        //given
        PostingRequestDto validPostingRequestDto =
            new PostingRequestDto("test title", "test content");

        //when
        int postedIndex = articleService.posting(validPostingRequestDto);

        //then
        assertThat(postedIndex).isEqualTo(0);
    }
}
