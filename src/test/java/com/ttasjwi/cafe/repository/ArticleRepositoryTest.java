package com.ttasjwi.cafe.repository;

import com.ttasjwi.cafe.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArticleRepositoryTest {

    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        this.articleRepository = new ArticleRepository();
    }

    @Test
    @DisplayName("저장 게시글의 인덱스로 조회 -> 같은 저장게시글 반환")
    void saveTest() {
        // given
        Article article = new Article("자바 두명 타요", "활활 잘 탄다~");
        int articleId = articleRepository.save(article);

        // when
        Article findArticle = articleRepository.findByArticleId(articleId);

        // then
        assertThat(article).isEqualTo(findArticle);
    }

    @Test
    @DisplayName("0이하의 아이디의 게시글 조회 -> NoSuchElementException 발생")
    void findByArticleIndex_FailureTest_When_less_Than_zero_index() {
        // then
        assertThatThrownBy(()->articleRepository.findByArticleId(0))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("size보다 큰 인덱스의 게시글 조회 -> NoSuchElementException 발생")
    void findByArticleIndex_FailureTest_When_greater_Than_size() {
        // given
        Article article1 = new Article("우리는 노예가 되지 않는다", "세상의 주인이 될 것이다");
        Article article2 = new Article("여러분 님이 무엇입니까...?", "님은 바로 호눅스를 말하는 것입니다 여러분");

        articleRepository.save(article1);
        articleRepository.save(article2);

        // when & then
        assertThatThrownBy(()->articleRepository.findByArticleId(3))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("findAll -> 저장한 인원만큼의 리스트 반환")
    void findAllTest() {
        // given
        Article article1 = new Article("선생님들 살려주세요", "회사 DB에 DROP DATABASE 해버렸어요;;");
        Article article2 = new Article("오늘의 점심 추천 받습니다...", "ㅜㅜ");

        articleRepository.save(article1);
        articleRepository.save(article2);

        // when
        List<Article> list = articleRepository.findAll();
        int size = list.size();

        // then
        assertThat(size).isEqualTo(2);
    }
}