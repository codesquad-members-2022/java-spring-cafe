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
            // arrange
            Long registeredId = 1L;
            String expectedRedirectUrl = "/users";
            when(memberService.join(any())).thenReturn(Optional.of(registeredId));

            // act
            ResultActions actions = mockMvc.perform(
                    MockMvcRequestBuilders.post("/users")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("userId", "jay")
                            .param("passwd", "1234")
                            .param("name", "김진완")
                            .param("email", "jay@naver.com")
            );

            // assert
            actions.andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl(expectedRedirectUrl));
        }

        @Test
        @DisplayName("중복된 userId 로 시도하면 실패하고, 회원가입 화면으로 돌아간다.")
        void join_failed() throws Exception {
            // arrange
            when(memberService.join(any())).thenThrow(new IllegalStateException("이미 존재하는 회원입니다."));

            // act
            ResultActions actions = mockMvc.perform(
                    MockMvcRequestBuilders.post("/users")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("userId", "jay")
                            .param("passwd", "1234")
                            .param("name", "김진완")
                            .param("email", "jay@naver.com")
            );

            // assert
            MvcResult mvcResult = actions.andExpect(status().is4xxClientError()).andReturn();

            ModelAndView modelAndView = mvcResult.getModelAndView();
            Map<String, Object> model = modelAndView.getModel();

            assertThat(model.containsKey("errorMessage")).isTrue();
            assertThat(model.get("errorMessage").toString()).contains("이미 존재하는 회원입니다.");
            assertThat(modelAndView.getViewName()).isEqualTo("member/form");
        }
    }

    @Nested
    @DisplayName("회원 목록 조회는")
    class FindMembersTest {
        @Test
        @DisplayName("회원이 있으면, 회원목록 데이터와 함께 회원 목록 화면으로 이동한다.")
        void findMembers_moveToView() throws Exception {
            // arrange
            String memberListViewName = "member/list";
            String membersKey = "members";
            List<Member> members = getMembers();
            when(memberService.findMembers()).thenReturn(Optional.of(members));

            // act
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/users"));

            // assert
            ModelAndView modelAndView = actions.andExpect(status().isOk())
                    .andReturn()
                    .getModelAndView();
            assertThat(modelAndView.getViewName()).isEqualTo(memberListViewName);
            assertThat(modelAndView.getModel().containsKey(membersKey)).isTrue();
            assertThat(modelAndView.getModel().get(membersKey)).isNotNull();
        }

        @Test
        @DisplayName("회원이 없어도, 회원 목록 화면으로 이동한다.")
        void findMembersReturnEmpty_moveToView() throws Exception {
            // arrange
            String memberListViewName = "member/list";
            when(memberService.findMembers()).thenReturn(Optional.empty());

            // act
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/users"));

            // assert
            ModelAndView modelAndView = actions.andExpect(status().isOk())
                    .andReturn()
                    .getModelAndView();
            assertThat(modelAndView.getViewName()).isEqualTo(memberListViewName);
        }

        private List<Member> getMembers() {
            return List.of(new Member.Builder()
                            .setId(1L)
                            .setUserId("jwkim")
                            .setPasswd("1234")
                            .setName("김진완")
                            .setEmail("wlsdhks0423@naver.com")
                            .setCreatedDate(LocalDateTime.now())
                            .setModifiedDate(LocalDateTime.now())
                            .build(),
                    new Member.Builder()
                            .setId(2L)
                            .setUserId("jay")
                            .setPasswd("1234")
                            .setName("김제이")
                            .setEmail("jay@naver.com")
                            .setCreatedDate(LocalDateTime.now())
                            .setModifiedDate(LocalDateTime.now())
                            .build());
        }
    }

    @Nested
    @DisplayName("회원 프로필 조회는")
    class FindProfileTest {

        @Test
        @DisplayName("존재하는 회원의 userId 가 path 로 들어오면, 회원 프로필 조회 페이지로 이동한다.")
        void findProfile_success() throws Exception {
            // arrange
            Member member = getMember();
            Long id = member.getId();
            when(memberService.findOne(any())).thenReturn(Optional.of(member));

            // act
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/users/" + id));

            // assert
            ModelAndView modelAndView = actions.andExpect(status().isOk())
                    .andReturn()
                    .getModelAndView();
            assertThat(modelAndView.getViewName()).isEqualTo("member/profile");
            assertThat(modelAndView.getModel().containsKey("member")).isTrue();
            assertThat(modelAndView.getModel().get("member")).isNotNull();
        }

        @Test
        @DisplayName("존재하지 않는 회원의 userId 가 path 로 들어오면 에러 메세지와 함께, 회원 목록 페이지로 이동한다.")
        void findProfile_failed() throws Exception {
            // arrange
            when(memberService.findOne(any())).thenReturn(Optional.empty());

            // act
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/users/1"));

            // assert
            MvcResult mvcResult = actions.andExpect(status().is4xxClientError()).andReturn();

            ModelAndView modelAndView = mvcResult.getModelAndView();
            Map<String, Object> model = modelAndView.getModel();

            assertThat(modelAndView.getViewName()).isEqualTo("member/list");
            assertThat(model.containsKey("errorMessage")).isTrue();
//            assertThat(model.get("errorMessage").toString()).contains("이미 존재하는 회원입니다.");
        }

        private Member getMember() {
            return new Member.Builder()
                    .setId(1L)
                    .setUserId("jwkim")
                    .setPasswd("1234")
                    .setName("김진완")
                    .setEmail("wlsdhks0423@naver.com")
                    .setCreatedDate(LocalDateTime.now())
                    .setModifiedDate(LocalDateTime.now())
                    .build();
        }

    }

}
