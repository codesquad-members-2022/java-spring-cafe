package com.kakao.cafe.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.NewArticleParam;
import com.kakao.cafe.exception.article.NoSuchArticleException;
import com.kakao.cafe.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.kakao.cafe.message.ArticleDomainMessage.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
@DisplayName("ArticleControllerUnitTest")
public class ArticleControllerUnitTest {

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

    @DisplayName("글쓰기 요청이 도착하면 등록 후 질문 글 목록 ('/') 으로 리다이렉트한다.")
    @ParameterizedTest(name = "{index} {displayName} user={0}")
    @MethodSource("paramsForWriteArticle")
    void writeArticle(NewArticleParam newArticleParam) throws Exception {
        Article article = newArticleParam.convertToArticle();
        given(service.add(newArticleParam)).willReturn(article);

        mvc.perform(post("/articles/write").params(convertToMultiValueMap(newArticleParam)))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/")
                );

        verify(service, only()).add(ArgumentMatchers.refEq(newArticleParam));
    }

    static Stream<Arguments> paramsForWriteArticle() {
        return Stream.of(
                Arguments.of(new NewArticleParam("writer1", "title1", "contents1")),
                Arguments.of(new NewArticleParam("writer2", "title2", "contents2")),
                Arguments.of(new NewArticleParam("writer3", "title3", "contents3"))
        );
    }

    @DisplayName("PathVariable 값인 id 으로 해당하는 글을 찾아 'qna/show' 에서 출력한다.")
    @Test
    void getDetailSuccess() throws Exception {
        int id = 1;
        Article article = articles.get(id - 1);
        given(service.search(id)).willReturn(article);

        mvc.perform(get("/articles/" + id))
                .andExpectAll(
                        model().attributeExists("article"),
                        model().attribute("article", article),
                        content().contentTypeCompatibleWith(MediaType.TEXT_HTML),
                        content().encoding(StandardCharsets.UTF_8),
                        status().isOk(),
                        view().name("qna/show")
                );

        verify(service, only()).search(id);
    }

    @DisplayName("PathVariable 값인 id 으로 해당하는 글이 존재하지 않으면 NoSuchArticleException 예외가 발생한다.")
    @Test
    void getDetailFail() throws Exception {
        int id = 4;
        given(service.search(id)).willThrow(new NoSuchArticleException(HttpStatus.OK, NO_SUCH_ARTICLE_MESSAGE));

        mvc.perform(get("/articles/" + id))
                .andExpectAll(
                        content().string(NO_SUCH_ARTICLE_MESSAGE),
                        status().isOk()
                );

        verify(service).search(id);
    }

    private MultiValueMap<String, String> convertToMultiValueMap(Object obj) {
        Map<String, String> map = new ObjectMapper().convertValue(obj, new TypeReference<>() {});
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.setAll(map);

        return params;
    }
}
