package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.ArticleWriteRequest;
import com.kakao.cafe.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ArticleServiceMockTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    @Test
    void write_메서드_만약_article이_들어온다면_저장소에_저장한다() {
        given(articleRepository.save(any()))
                .willReturn(new Article(new ArticleWriteRequest("쿠킴1", "제목1", "본문1")));

        Article result = articleService.write(new ArticleWriteRequest("쿠킴1", "제목1", "본문1"));

        then(articleRepository).should(times(1)).save(any());
        assertThat(result.getWriter()).isEqualTo("쿠킴1");
        assertThat(result.getTitle()).isEqualTo("제목1");
        assertThat(result.getContents()).isEqualTo("본문1");
    }

    @Test
    void findAricles_메서드_저장소의_모든_article를_리스트로_리턴한다() {
        given(articleRepository.findAll())
                .willReturn(List.of(new Article(new ArticleWriteRequest("쿠킴1", "제목1", "본문1")),
                        new Article(new ArticleWriteRequest("쿠킴2", "제목2", "본문2"))));

        List<Article> result = articleService.findArticles();

        then(articleRepository).should(times(1)).findAll();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findArticle_메서드_만약_유효한_articleId가_주어진다면_해당_Article를_리턴한다() {
        given(articleRepository.findByArticleId(any()))
                .willReturn(Optional.ofNullable(new Article(new ArticleWriteRequest("쿠킴1", "제목1", "본문1"))));

        Article result = articleService.findArticle(any());

        then(articleRepository).should(times(1)).findByArticleId(any());
        assertThat(result.getWriter()).isEqualTo("쿠킴1");
        assertThat(result.getTitle()).isEqualTo("제목1");
        assertThat(result.getContents()).isEqualTo("본문1");
    }

    @Test
    void findArticle_메서드_만약_유효하지않은_articleId가_주어진다면_예외를던진다() {
        given(articleRepository.findByArticleId(-1))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> articleService.findArticle(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
