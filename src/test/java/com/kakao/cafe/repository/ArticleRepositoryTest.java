package com.kakao.cafe.repository;

import com.kakao.cafe.controller.ArticleDto;
import com.kakao.cafe.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest //이 어노테이션 기능 알아보기
class ArticleRepositoryTest {

    private final ArticleRepository articleRepository;

    @Autowired
    ArticleRepositoryTest(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @BeforeEach
    void setUp() {
        articleRepository.deleteAll();
    }

    @Test
    @DisplayName("유효한 게시글 정보를 입력받으면 ArticleRepository를 통해 저장한다.")
    void save() {
        ArticleDto requestDto = new ArticleDto();
        requestDto.setWriter("pio");
        requestDto.setTitle("테스트 게시글");
        requestDto.setContents("test123");

        Article article = new Article(requestDto);
//        articleRepository.save(article);
        articleRepository.findById(article.getId()).orElseThrow();
    }
}