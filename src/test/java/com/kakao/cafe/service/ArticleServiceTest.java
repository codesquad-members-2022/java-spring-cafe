package com.kakao.cafe.service;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.domain.article.ArticleDto;
import com.kakao.cafe.repository.article.ArticleMemoryRepository;
import com.kakao.cafe.repository.article.ArticleRepository;
import com.kakao.cafe.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ArticleServiceTest {

    ArticleRepository repository = new ArticleMemoryRepository();
    ArticleService service = new ArticleService(repository);

    @Test
    @DisplayName("아티클 Dto를 넘기면 아티클을 저장한다.")
    void createArticle() {
        ArticleDto articleDto = new ArticleDto("포키", "질문있어요", "포키포키");

        ArticleDto article = service.createArticle(articleDto);

        assertThat(article.getWriter()).isEqualTo("포키");
        assertThat(article.getTitle()).isEqualTo("질문있어요");
        assertThat(article.getContents()).isEqualTo("포키포키");
    }

    @Test
    @DisplayName("제목 혹은 컨텐츠가 비었으면 예외를 던진다.")
    void blankTitleOrContentsException() {
        ArticleDto blankTitleDto = new ArticleDto("포키", "", "포키포키");
        ArticleDto blankContentDto = new ArticleDto("포키", "질문있어요", "");


        assertThatThrownBy(() -> service.createArticle(blankTitleDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제목 혹은 컨텐츠가 비어있습니다.");
        assertThatThrownBy(() -> service.createArticle(blankContentDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("제목 혹은 컨텐츠가 비어있습니다.");
    }

    @Test
    @DisplayName("찾는 데이터가 없으면 예외를 던진다.")
    void notFoundException() {
        assertThatThrownBy(() -> service.findSingleArticle(10L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("해당 아티클이 존재하지 않습니다.");
    }


}
