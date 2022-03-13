package com.kakao.cafe.exception;

import com.kakao.cafe.users.controller.UserController;
import com.kakao.cafe.users.exception.UserNotFountException;
import com.kakao.cafe.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("GlobalExceptionHandler 단위 테스트")
public class GlobalExceptionHandlerTest {

    @Nested
    @DisplayName("회원 관련 기능에서")
    class UserControllerTest{

        @Mock
        private UserService userService;

        @InjectMocks
        private UserController userController;

        private MockMvc mockMvc;

        @BeforeEach
        void setup() {
            mockMvc = MockMvcBuilders.standaloneSetup(userController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
        }

        @Nested
        @DisplayName("회원 프로필 조회를 할 때")
        class FindProfileTest{
            @Test
            @DisplayName("존재하지 않는 회원을 조회하면 404 페이지로 이동한다.")
            void findProfile_failed() throws Exception {
                // arrange
                when(userService.findOne(any())).thenThrow(UserNotFountException.class);

                // act
                ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/users/1"));

                // assert
                actions.andExpect(status().is4xxClientError())
                        .andExpect(GlobalExceptionHandlerTest.this::assert404Page);
            }
        }
    }

    private void assert404Page(MvcResult result) {
        ModelAndView modelAndView = result.getModelAndView();
        assertThat(modelAndView).isNotNull();
        assertThat(modelAndView.getViewName()).isEqualTo("error/404");
    }
}
