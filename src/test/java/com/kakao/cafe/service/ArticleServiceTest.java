package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.*;

import com.kakao.cafe.controller.dto.PostingRequestDto;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repository.MemoryArticleRepository;
import java.util.List;
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

    @Test
    void findPosts() {
        //given
        User loggedInUser = new User("test@user.com", "testuser", "test");
        for (int i = 1; i <= 10; i++) {
            String title = "test title" + i;
            String content = "test content" + i;
            Article article = new Article(loggedInUser, title, content);
            articleRepository.save(article);
        }

        //when
        List<Article> posts = articleService.findPosts();

        //then
        assertThat(posts.size()).isEqualTo(10);
    }
}
