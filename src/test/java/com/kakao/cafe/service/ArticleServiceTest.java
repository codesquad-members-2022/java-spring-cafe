package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.*;

import com.kakao.cafe.controller.dto.ArticleDto;
import com.kakao.cafe.controller.dto.PostingRequestDto;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ArticleServiceTest {

    private final ArticleRepository articleRepository;
    private final ArticleService articleService;

    @Autowired
    public ArticleServiceTest(ArticleRepository articleRepository,
        ArticleService articleService) {
        this.articleRepository = articleRepository;
        this.articleService = articleService;
    }

    @Test
    @DisplayName("게시글을 저장하고, 저장된 게시글의 id를 리턴한다.")
    void posting() {
        //given
        PostingRequestDto validPostingRequestDto =
            new PostingRequestDto("test title", "test content");

        //when
        int savedArticleCount = articleService.posting(validPostingRequestDto);

        //then
        assertThat(savedArticleCount).isEqualTo(4);
    }

    @Test
    @DisplayName("게시글을 존재하는 id로 조회하면, 해당 id를 가진 게시글의 조회수를 1 올려주고 해당 게시글을 리턴한다.")
    void valid_read() {
        //given
        Article givenArticle = new Article("Anonymous", "test title", "test content");
        int givenId = articleRepository.save(givenArticle);

        //when
        ArticleDto result = articleService.read(givenId);

        //then
        assertThat(result.getViewCount()).isEqualTo(givenArticle.getViewCount() + 1);
    }

    @Test
    @DisplayName("게시글을 존재하지 않는 id로 조회하면, \"해당 아이디를 가진 게시글이 존재하지 않습니다.\"라는 NoSuchElementException을 던진다.")
    void invalid_read() {
        //given
        int givenId = -1;

        //when
        ThrowingCallable result = () -> articleService.read(givenId);

        //then
        assertThatThrownBy(result)
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("해당 아이디를 가진 게시글이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("게시글을 전부 조회하면, 저장된 게시글 전부를 List에 담아 리턴한다.")
    void findPosts() {
        //given

        //when
        List<ArticleDto> posts = articleService.findPosts();

        //then
        assertThat(posts.size()).isEqualTo(3);
    }
}
