package com.kakao.cafe.service;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class ArticleServiceIntegrationTest {

    @Autowired ArticleService articleService;
    @Autowired
    ArticleRepository articleRepository;

    @Test
    @DisplayName("글 저장시 올바르게 저장되는지 확인한다")
    void 작성글_저장(){
        Article article = new Article("글쓴이","제목","내용");
        articleService.saveArticle(article);

        Article findOneArticle = articleService.loadOneArticle(article.getId());
        assertThat(article.getTitle()).isEqualTo(findOneArticle.getTitle());
        assertThat(article.getWriter()).isEqualTo(findOneArticle.getWriter());
        assertThat(article.getContent()).isEqualTo(findOneArticle.getContent());
    }

}
