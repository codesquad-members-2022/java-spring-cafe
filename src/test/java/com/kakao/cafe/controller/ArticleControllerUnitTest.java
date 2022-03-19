package com.kakao.cafe.controller;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.ModifiedArticleParam;
import com.kakao.cafe.dto.NewArticleParam;
import com.kakao.cafe.exception.article.NoSuchArticleException;
import com.kakao.cafe.exception.article.RemoveArticleException;
import com.kakao.cafe.exception.article.SaveArticleException;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.util.DomainMapper;
import org.apache.logging.log4j.util.Strings;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static com.kakao.cafe.message.ArticleDomainMessage.*;
import static com.kakao.cafe.util.Convertor.convertToMultiValueMap;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
@DisplayName("ArticleControllerUnitTest")
public class ArticleControllerUnitTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ArticleService service;

    MockHttpSession session;

    List<Article> articles;

    DomainMapper<Article> articleMapper = new DomainMapper<>();

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        articles = List.of(
                new Article(1, "writer1", "title1", "contents1", LocalDate.now()),
                new Article(2, "writer2", "title2", "contents2", LocalDate.now()),
                new Article(3, "writer3", "title3", "contents3", LocalDate.now())
        );
    }

    /*
        write
     */
    @DisplayName("로그인한 클라이언트의 글쓰기 요청이 오면 등록 후 질문 글 목록 ('/') 으로 리다이렉트한다.")
    @ParameterizedTest(name = "{index} {displayName} user={0}")
    @MethodSource("paramsForWriteArticle")
    void writeArticleSuccess(NewArticleParam newArticleParam) throws Exception {
        // given
        Article article = articleMapper.convertToDomain(newArticleParam, Article.class);
        given(service.add(newArticleParam)).willReturn(article);

        session.setAttribute("userInfo",
                new User(anyInt(), Strings.EMPTY, Strings.EMPTY, Strings.EMPTY, Strings.EMPTY));

        // when
        mvc.perform(post("/articles")
                        .params(convertToMultiValueMap(newArticleParam))
                        .session(session))

                // then
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/")
                );

        verify(service).add(ArgumentMatchers.refEq(newArticleParam));
    }

    @DisplayName("로그인하지 않은 클라이언트의 글쓰기 요청이 오면 로그인 화면 ('/users/login') 으로 리다이렉트한다.")
    @Test
    void writeArticleFailNotLogin() throws Exception {
        // given
        NewArticleParam newArticleParam = new NewArticleParam("writer", "title", "contents");

        // when
        mvc.perform(post("/articles").params(convertToMultiValueMap(newArticleParam))
                        .session(session))

                // then
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users/login")
                );
    }

    static Stream<Arguments> paramsForWriteArticle() {
        return Stream.of(
                Arguments.of(new NewArticleParam("writer1", "title1", "contents1")),
                Arguments.of(new NewArticleParam("writer2", "title2", "contents2")),
                Arguments.of(new NewArticleParam("writer3", "title3", "contents3"))
        );
    }

    /*
        getDetail
     */
    @DisplayName("로그인한 클라이언트의 글 상세 보기 요청이 오고 파라미터 값인 id 에 해당하는 글을 찾아 'qna/show' 에서 출력한다.")
    @Test
    void getDetailSuccess() throws Exception {
        // given
        int id = 1;
        Article article = articles.get(id - 1);
        given(service.search(id)).willReturn(article);

        session.setAttribute("userInfo",
                new User(anyInt(), Strings.EMPTY, Strings.EMPTY, Strings.EMPTY, Strings.EMPTY));

        // when
        mvc.perform(get("/articles/" + id)
                        .session(session))

                // then
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

    @DisplayName("로그인한 클라이언트의 글 상세 보기 요청이 오고 PathVariable 값인 id 으로 해당하는 글이 존재하지 않으면 NoSuchArticleException 예외가 발생한다.")
    @Test
    void getDetailFail() throws Exception {
        // given
        int id = 4;
        given(service.search(id)).willThrow(new NoSuchArticleException(HttpStatus.OK, NO_SUCH_ARTICLE_MESSAGE));

        session.setAttribute("userInfo",
                new User(anyInt(), Strings.EMPTY, Strings.EMPTY, Strings.EMPTY, Strings.EMPTY));

        // when
        mvc.perform(get("/articles/" + id)
                        .session(session))

                // then
                .andExpectAll(
                        content().string(NO_SUCH_ARTICLE_MESSAGE),
                        status().isOk()
                );

        verify(service).search(id);
    }

    /*
        modify
     */
    @DisplayName("로그인한 클라이언트의 자기 게시글 수정 요청이 도착하면 변경 사항을 반영 후 질문 글 목록 ('/') 으로 리다이렉트한다.")
    @Test
    void modifyArticleSuccess() throws Exception {
        // given
        int id = 1;
        LocalDate currentDate = LocalDate.now();
        ModifiedArticleParam modifiedArticleParam
                = new ModifiedArticleParam(1, "writer", "title", "contents", currentDate);

        Article article = articleMapper.convertToDomain(modifiedArticleParam, Article.class);
        given(service.update(modifiedArticleParam)).willReturn(article);

        session.setAttribute("userInfo",
                new User(anyInt(), article.getWriter(), Strings.EMPTY, Strings.EMPTY, Strings.EMPTY));

        // when
        mvc.perform(put("/articles/" + id).params(convertToMultiValueMap(modifiedArticleParam))
                        .session(session))

                // then
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/")
                );

        verify(service).update(ArgumentMatchers.refEq(modifiedArticleParam));
    }

    @DisplayName("로그인한 클라이언트의 자기 게시글 수정 요청이 도착했지만 정상적으로 처리되지 않았을 경우 SaveArticleException 예외가 발생한다.")
    @Test
    void modifyArticleFail() throws Exception {
        // given
        int id = 4;
        LocalDate currentDate = LocalDate.now();
        ModifiedArticleParam modifiedArticleParam
                = new ModifiedArticleParam(4, "writer", "title", "contents", currentDate);

        Article article = articleMapper.convertToDomain(modifiedArticleParam, Article.class);

        given(service.update(ArgumentMatchers.refEq(modifiedArticleParam)))
                .willThrow(new SaveArticleException(HttpStatus.BAD_GATEWAY, UPDATE_FAIL_MESSAGE));

        session.setAttribute("userInfo",
                new User(anyInt(), article.getWriter(), Strings.EMPTY, Strings.EMPTY, Strings.EMPTY));

        // when
        mvc.perform(put("/articles/" + id)
                        .params(convertToMultiValueMap(modifiedArticleParam))
                        .session(session))

                // then
                .andExpectAll(
                        content().string(UPDATE_FAIL_MESSAGE),
                        status().isBadGateway()
                );

        verify(service).update(ArgumentMatchers.refEq(modifiedArticleParam));
    }

    @DisplayName("로그인한 클라이언트의 본인이 작성하지 않은 게시글에 대한 수정 요청이 오면 '/error/403' 로 리다이렉트한다.")
    @Test
    void modifyArticleFail2() throws Exception {
        // given
        int id = 3;
        LocalDate currentDate = LocalDate.now();
        ModifiedArticleParam modifiedArticleParam
                = new ModifiedArticleParam(3, "otherUser", "title", "contents", currentDate);

        session.setAttribute("userInfo",
                new User(1, "user", Strings.EMPTY, Strings.EMPTY, Strings.EMPTY));

        // when
        mvc.perform(put("/articles/" + id)
                        .params(convertToMultiValueMap(modifiedArticleParam))
                        .session(session))

                // then
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/error/403")
                );
    }

    @DisplayName("로그인하지 않은 클라이언트의 게시글 수정 요청이 도착하면 로그인 화면 ('users/login') 으로 리다이렉트한다.")
    @Test
    void modifyArticleFailNotLogin() throws Exception {
        // given
        int id = 1;
        LocalDate currentDate = LocalDate.now();
        ModifiedArticleParam modifiedArticleParam
                = new ModifiedArticleParam(1, "writer", "title", "contents", currentDate);

        // when
        mvc.perform(put("/articles/" + id)
                        .params(convertToMultiValueMap(modifiedArticleParam))
                        .session(session))

                // then
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users/login")
                );
    }

    /*
     * delete
     */
    @DisplayName("로그인한 클라이언트의 자기 게시글 삭제 요청이 도착하면 삭제 후 질문 글 목록 ('/') 으로 리다이렉트한다.")
    @Test
    void deleteArticleSuccess() throws Exception {
        // given
        int articleId = 1;
        String writer = "writer";
        doNothing().when(service).remove(articleId);

        session.setAttribute("userInfo", new User(1, writer, Strings.EMPTY, Strings.EMPTY, Strings.EMPTY));

        // when
        mvc.perform(delete("/articles/" + articleId)
                        .param("writer", writer)
                        .session(session))

                // then
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/")
                );

        verify(service).remove(articleId);
    }

    @DisplayName("로그인한 클라이언트의 자기 게시글 삭제 요청이 도착했지만 정상적으로 처리되지 않았을 경우 RemoveArticleException 예외가 발생한다.")
    @Test
    void deleteArticleFail() throws Exception {
        // given
        int articleId = 1;
        String writer = "writer";
        doThrow(new RemoveArticleException(HttpStatus.BAD_GATEWAY, REMOVE_FAIL_MESSAGE)).when(service).remove(articleId);

        session.setAttribute("userInfo", new User(1, writer, Strings.EMPTY, Strings.EMPTY, Strings.EMPTY));

        // when
        mvc.perform(delete("/articles/" + articleId)
                        .param("writer", writer)
                        .session(session))

                // then
                .andExpectAll(
                        content().string(REMOVE_FAIL_MESSAGE),
                        status().isBadGateway()
                );

        verify(service).remove(articleId);
    }

    @DisplayName("로그인한 클라이언트의 본인이 작성하지 않은 게시글에 대한 삭제 요청이 오면 '/error/403' 로 리다이렉트한다.")
    @Test
    void deleteArticleFail2() throws Exception {
        // given
        int articleId = 1;
        session.setAttribute("userInfo", new User(1, "writer", Strings.EMPTY, Strings.EMPTY, Strings.EMPTY));

        // when
        mvc.perform(delete("/articles/" + articleId)
                        .param("writer", "otherWriter")
                        .session(session))

                // then
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/error/403")
                );
    }

    @DisplayName("로그인하지 않은 클라이언트의 게시글 삭제 요청이 도착하면 로그인 화면 ('users/login') 으로 리다이렉트한다.")
    @Test
    void deleteArticleFailNotLogin() throws Exception {
        // given
        int articleId = 1;
        doNothing().when(service).remove(articleId);

        // when
        mvc.perform(delete("/articles/" + articleId)
                        .param("writer", "writer")
                        .session(session))

                // then
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users/login")
                );
    }
}
