package com.kakao.cafe.web.service.article;

import com.kakao.cafe.core.domain.article.Article;
import com.kakao.cafe.core.repository.article.ArticleRepository;
import com.kakao.cafe.web.controller.article.dto.ArticleWriteRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    ArticleService articleService;

    @Mock
    ArticleRepository articleRepository;

    private Article article;

    @BeforeEach
    void init() {
        article = getArticle();
    }

    private Article getArticle() {
        return new Article.Builder()
                .id(1)
                .title("반갑습니다.")
                .content("반갑습니다.")
                .writer("jun")
                .createAt(LocalDateTime.now())
                .viewCount(0)
                .build();
    }

    @Test
    @DisplayName("해당 회원이 존재하면 그 회원을 반환한다.")
    void findById_조회성공() {
        articleRepository.save(getArticle());
        assertNotNull(articleRepository.findById(1));
    }

    @Test
    @DisplayName("해당 회원이 존재하지 않으면 예외를 발생시킨다.")
    void findById_조회실패() {
        assertThatThrownBy(() -> articleService.findById(Integer.MIN_VALUE))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No value present");
    }

    @Test
    @DisplayName("findAll 메서드를 호출하면 List<Article>이 반환된다.")
    void findAll() {
        ArticleWriteRequest requestA = new ArticleWriteRequest("반갑습니다.", "반갑습니다.", "jun");
        ArticleWriteRequest requestB = new ArticleWriteRequest("반갑습니다.", "반갑습니다.", "kim");
        given(articleRepository.findAll()).willReturn(List.of(requestA.toEntity(), requestB.toEntity()));

        List<Article> articles = articleService.findAll();
        assertThat(articles.size()).isEqualTo(2);
    }
}