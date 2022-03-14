package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.*;

import com.kakao.cafe.controller.dto.PostDto;
import com.kakao.cafe.controller.dto.PostingRequestDto;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.MemoryArticleRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
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
        assertThat(postedIndex).isEqualTo(1);
    }

    @Test
    void valid_read() {
        //given
        Article givenArticle = new Article("Anonymous", "test title", "test content");
        int givenId = articleRepository.save(givenArticle);

        //when
        PostDto result = articleService.read(givenId);

        //then
        assertThat(result.getViewCount()).isEqualTo(givenArticle.getViewCount() + 1);
    }

    @Test
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
    void findPosts() {
        //given
        for (int i = 1; i <= 10; i++) {
            String title = "test title" + i;
            String content = "test content" + i;
            Article article = new Article("Anonymous", title, content);
            articleRepository.save(article);
        }

        //when
        List<PostDto> posts = articleService.findPosts();

        //then
        assertThat(posts.size()).isEqualTo(10);
    }
}
