package com.kakao.cafe.users;

import com.kakao.cafe.users.domain.User;
import com.kakao.cafe.users.exception.UserDuplicatedException;
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
@DisplayName("UserController 단위 테스트")
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
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
            when(userService.join(any())).thenReturn(Optional.of(registeredId));

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
            when(userService.join(any())).thenThrow(new UserDuplicatedException("이미 존재하는 회원입니다."));

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
            assertThat(modelAndView.getViewName()).isEqualTo("user/form");
        }
    }

    @Nested
    @DisplayName("회원 목록 조회는")
    class FindUsersTest {
        @Test
        @DisplayName("회원이 있으면, 회원목록 데이터와 함께 회원 목록 화면으로 이동한다.")
        void findUsers_moveToView() throws Exception {
            // arrange
            String userListViewName = "user/list";
            String usersKey = "users";
            List<User> users = getUsers();
            when(userService.findUsers()).thenReturn(Optional.of(users));

            // act
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/users"));

            // assert
            ModelAndView modelAndView = actions.andExpect(status().isOk())
                    .andReturn()
                    .getModelAndView();
            assertThat(modelAndView.getViewName()).isEqualTo(userListViewName);
            assertThat(modelAndView.getModel().containsKey(usersKey)).isTrue();
            assertThat(modelAndView.getModel().get(usersKey)).isNotNull();
        }

        @Test
        @DisplayName("회원이 없어도, 회원 목록 화면으로 이동한다.")
        void findUsersReturnEmpty_moveToView() throws Exception {
            // arrange
            String userListViewName = "user/list";
            when(userService.findUsers()).thenReturn(Optional.empty());

            // act
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/users"));

            // assert
            ModelAndView modelAndView = actions.andExpect(status().isOk())
                    .andReturn()
                    .getModelAndView();
            assertThat(modelAndView.getViewName()).isEqualTo(userListViewName);
        }

        private List<User> getUsers() {
            return List.of(new User.Builder()
                            .setId(1L)
                            .setUserId("jwkim")
                            .setPasswd("1234")
                            .setName("김진완")
                            .setEmail("wlsdhks0423@naver.com")
                            .setCreatedDate(LocalDateTime.now())
                            .setModifiedDate(LocalDateTime.now())
                            .build(),
                    new User.Builder()
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
            User user = getUser();
            Long id = user.getId();
            when(userService.findOne(any())).thenReturn(Optional.of(user));

            // act
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/users/" + id));

            // assert
            ModelAndView modelAndView = actions.andExpect(status().isOk())
                    .andReturn()
                    .getModelAndView();
            assertThat(modelAndView.getViewName()).isEqualTo("user/profile");
            assertThat(modelAndView.getModel().containsKey("user")).isTrue();
            assertThat(modelAndView.getModel().get("user")).isNotNull();
        }

        @Test
        @DisplayName("존재하지 않는 회원의 userId 가 path 로 들어오면 에러 메세지와 함께, 회원 목록 페이지로 이동한다.")
        void findProfile_failed() throws Exception {
            // arrange
            when(userService.findOne(any())).thenReturn(Optional.empty());

            // act
            ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/users/1"));

            // assert
            MvcResult mvcResult = actions.andExpect(status().is4xxClientError()).andReturn();

            ModelAndView modelAndView = mvcResult.getModelAndView();
            Map<String, Object> model = modelAndView.getModel();

            assertThat(modelAndView.getViewName()).isEqualTo("user/list");
            assertThat(model.containsKey("errorMessage")).isTrue();
//            assertThat(model.get("errorMessage").toString()).contains("이미 존재하는 회원입니다.");
        }

        private User getUser() {
            return new User.Builder()
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
