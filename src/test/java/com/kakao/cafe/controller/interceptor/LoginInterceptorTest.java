package com.kakao.cafe.controller.interceptor;

import com.kakao.cafe.controller.ArticleController;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ArticleController.class)
class LoginInterceptorTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    ArticleService articleService;

    @Test
    @DisplayName("세션이 없으면 로그인 페이지로 리다이렉트된다")
    void preHandle() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mvc.perform(get("/qna")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("세션이 있으면 작성 페이지가 출력된다")
    void preHandlePass() throws Exception {
        MockHttpSession session = new MockHttpSession();
        User user = new User("testId", "1234", "test", "test@gmail.com");
        session.setAttribute("sessionUser", user);

        mvc.perform(get("/qna")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("qna/form"))
                .andDo(MockMvcResultHandlers.print());
    }
}
