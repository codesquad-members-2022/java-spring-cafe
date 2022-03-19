package com.kakao.cafe.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.dto.ArticleRequestDto;
import com.kakao.cafe.dto.ArticleResponseDto;
import com.kakao.cafe.dto.UserResponseDto;
import com.kakao.cafe.service.ArticleService;
import com.kakao.cafe.service.AuthService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@DisplayName("ArticleController 단위 테스트")
@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private AuthService authService;

    @DisplayName("사용자가 게시글 저장을 요청하면 게시글 정보를 저장하고 /로 리다이렉트한다.")
    @Test
    void 게시글_저장() throws Exception {
        // given
        MultiValueMap<String, String> articleParams = new LinkedMultiValueMap<>();
        articleParams.add("userId", "ikjo");
        articleParams.add("writer", "조명익");
        articleParams.add("title", "java");
        articleParams.add("contents", "java is fun");

        Article article = new Article(1, "ikjo", "조명익", "java", "java is fun", LocalDateTime.now());
        given(articleService.upload(any(ArticleRequestDto.class))).willReturn(article);

        // when
        ResultActions resultActions = mockMvc.perform(post("/questions").params(articleParams));

        // then
        resultActions.andExpect(redirectedUrl("/"));
    }

    @DisplayName("사용자가 특정 게시글 정보를 요청하면 model과 /qna/show view를 반환한다.")
    @Test
    void 특정_게시글_조회() throws Exception {
        // given
        ArticleResponseDto articleResponseDto = new ArticleResponseDto( 1, "ikjo", "조명익","java", "java is fun", LocalDateTime.now());
        given(articleService.findOne(1)).willReturn(articleResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(get("/articles/1"));

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(view().name("qna/show"))
            .andExpect(model().attribute("article", articleResponseDto));
    }

    @DisplayName("사용자가 특정 게시글 수정을 요청하면 게시글 수정 화면을 응답한다.")
    @Test
    void 게시글_수정_화면_조회() throws Exception {
        // given
        ArticleResponseDto articleResponseDto = new ArticleResponseDto( 1, "ikjo", "조명익","java", "java is fun", LocalDateTime.now());
        given(articleService.findOne(1)).willReturn(articleResponseDto);

        UserResponseDto userResponseDto = new UserResponseDto("ikjo", "조명익", "auddlr100@naver.com");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("sessionUser", userResponseDto);
        doNothing().when(authService).validateUserIdOfSession("ikjo", userResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(get("/questions/1/form").session(session));

        // then
        verify(authService).validateUserIdOfSession("ikjo", userResponseDto);
        resultActions.andExpect(status().isOk())
            .andExpect(view().name("qna/updateForm"))
            .andExpect(model().attribute("article", articleResponseDto));
    }

    @DisplayName("사용자가 특정 게시글 수정을 요청 후 정상 처리 시 해당 게시글 상세 보기 화면으로 리다이렉트한다.")
    @Test
    void 게시글_수정() throws Exception {
        // given
        MultiValueMap<String, String> articleParams = new LinkedMultiValueMap<>();
        articleParams.add("userId", "ikjo");
        articleParams.add("writer", "조명익");
        articleParams.add("title", "java");
        articleParams.add("contents", "java is fun");

        UserResponseDto userResponseDto = new UserResponseDto("ikjo", "조명익", "auddlr100@naver.com");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("sessionUser", userResponseDto);
        doNothing().when(authService).validateUserIdOfSession("ikjo", userResponseDto);

        ArticleRequestDto articleRequestDto = new ArticleRequestDto( "ikjo", "조명익","java", "java is fun");
        given(articleService.update(1, articleRequestDto)).willReturn(articleRequestDto.convertToDomain(1));

        // when
        ResultActions resultActions = mockMvc.perform(put("/questions/1/update")
            .session(session).params(articleParams));

        // then
        verify(authService).validateUserIdOfSession("ikjo", userResponseDto);
        resultActions.andExpect(redirectedUrl("/articles/1"));
    }

    @DisplayName("사용자가 특정 게시글 삭제를 요청하면 정상 처리 시 메인 화면을 응답한다.")
    @Test
    void 게시글_삭제() throws Exception {
        // given
        ArticleResponseDto articleResponseDto = new ArticleResponseDto( 1, "ikjo", "조명익","java", "java is fun", LocalDateTime.now());
        given(articleService.findOne(1)).willReturn(articleResponseDto);

        UserResponseDto userResponseDto = new UserResponseDto("ikjo", "조명익", "auddlr100@naver.com");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("sessionUser", userResponseDto);
        doNothing().when(authService).validateUserIdOfSession("ikjo", userResponseDto);
        doNothing().when(articleService).delete(1);

        // when
        ResultActions resultActions = mockMvc.perform(delete("/questions/1").param("id", "1").session(session));

        // then
        verify(authService).validateUserIdOfSession("ikjo", userResponseDto);
        verify(articleService).delete(1);
        resultActions.andExpect(redirectedUrl("/"));
    }
}
