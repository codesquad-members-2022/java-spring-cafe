package com.kakao.cafe.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kakao.cafe.domain.article.Article;
import com.kakao.cafe.repository.ArticleRepository;

@ExtendWith(SpringExtension.class) // MockitoExtension을 포함하는 개념
class ArticleServiceTest {

    @InjectMocks
    ArticleService articleService;

    @Mock
    ArticleRepository articleRepository;

    Article article1;
    Article article2;

    @BeforeEach
    void init() {
        article1 = new Article("lucid", "title1", "blabla");
        article2 = new Article("elon", "title2", "blabla~~");
    }

}