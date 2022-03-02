package com.kakao.cafe.controller.member;

import com.kakao.cafe.domain.member.Member;
import com.kakao.cafe.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberController 단위 테스트")
class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Nested
    @DisplayName("회원가입은")
    class RegisterTest{
        @Test
        @DisplayName("모든 데이터를 정상적으로 넣으면 성공한다.")
        void join_success() throws Exception {
            // given
            Long registeredId = 1L;
            String expectedRedirectUrl = "/users";
            when(memberService.join(any())).thenReturn(Optional.of(registeredId));

            // when
            ResultActions actions = mockMvc.perform(
                    MockMvcRequestBuilders.post("/users")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("userId", "jay")
                            .param("passwd", "1234")
                            .param("name", "김진완")
                            .param("email", "jay@naver.com")
            );

            // then
            actions.andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(expectedRedirectUrl));
        }

        @Test
        @DisplayName("중복된 userId 로 시도하면 실패하고, 회원가입 화면으로 돌아간다.")
        void join_failed() throws Exception {
            // given
            when(memberService.join(any())).thenThrow(new IllegalStateException("이미 존재하는 회원입니다."));

            // when
            ResultActions actions = mockMvc.perform(
                    MockMvcRequestBuilders.post("/users")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("userId", "jay")
                            .param("passwd", "1234")
                            .param("name", "김진완")
                            .param("email", "jay@naver.com")
            );

            // then
            MvcResult mvcResult = actions.andExpect(status().is4xxClientError()).andReturn();

            ModelAndView modelAndView = mvcResult.getModelAndView();
            Map<String, Object> model = modelAndView.getModel();

            assertThat(model.containsKey("errorMessage")).isTrue();
            assertThat(model.get("errorMessage").toString()).contains("이미 존재하는 회원입니다.");
            assertThat(modelAndView.getViewName()).isEqualTo("member/form");
        }
    }
}
