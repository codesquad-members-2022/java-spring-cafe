package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.LoginParam;
import com.kakao.cafe.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@DisplayName("HomeControllerUnitTest")
public class HomeControllerUnitTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ArticleService service;

    List<Article> articles;

    @BeforeEach
    void setUp() {
        articles = List.of(
                new Article(1, "writer1", "title1", "contents1", LocalDate.now()),
                new Article(2, "writer2", "title2", "contents2", LocalDate.now()),
                new Article(3, "writer3", "title3", "contents3", LocalDate.now())
        );
    }

    @DisplayName("'/' 로 요청이 들어오면 질문 글 목록 (qna/list.html) 페이지로 이동한다.")
    @Test
    void getArticles() throws Exception {
        given(service.searchAll()).willReturn(articles);

        mvc.perform(get("/"))
                .andExpectAll(
                        model().attributeExists("articles"),
                        model().attribute("articles", articles),
                        content().contentTypeCompatibleWith(MediaType.TEXT_HTML),
                        content().encoding(StandardCharsets.UTF_8),
                        status().isOk(),
                        view().name("qna/list")
                );

        verify(service, only()).searchAll();
    }
}
