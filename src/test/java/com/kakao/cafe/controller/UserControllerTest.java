package com.kakao.cafe.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.repositoryimpl.VolatilityUserRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.kakao.cafe.message.UserMessage.EXISTENT_ID_MESSAGE;
import static com.kakao.cafe.message.UserMessage.NON_EXISTENT_ID_MESSAGE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    final static int EXISTING_USERS_COUNT = 4;

    @Autowired
    MockMvc mvc;

    @Autowired
    VolatilityUserRepositoryImpl userRepository;

    static List<User> users;

    @BeforeAll
    static void init() {
        users = new ArrayList<>();
        for (int i = 0; i < EXISTING_USERS_COUNT; ++i) {
            users.add(User.builder("user" + (i + 1))
                    .name("name" + (i + 1))
                    .email("user" + (i + 1) + "@gmail.com")
                    .build()
            );
        }
    }

    @BeforeEach
    void setUp() {
        userRepository.clear();
        users.forEach(userRepository::save);
    }

    @DisplayName("미등록 사용자가 회원가입을 요청하면 사용자 추가를 완료한 후 사용자 목록 페이지로 이동한다.")
    @ParameterizedTest(name ="{index} {displayName} user={0}")
    @MethodSource("params4SignUpSuccess")
    void signUpSuccess(User user) throws Exception {
        mvc.perform(post("/users/register").params(convertToMultiValueMap(user)))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );
    }
    static Stream<Arguments> params4SignUpSuccess() {
        return Stream.of(
                Arguments.of(User.builder("user5").name("name5").email("user5@gmail.com").build()),
                Arguments.of(User.builder("user6").name("name6").email("user6@gmail.com").build()),
                Arguments.of(User.builder("user7").name("name7").email("user7@gmail.com").build()),
                Arguments.of(User.builder("user8").name("name8").email("user8@gmail.com").build())
        );
    }

    @DisplayName("등록된 사용자가 회원가입을 요청하면 BadRequest를 응답 받는다.")
    @ParameterizedTest(name ="{index} {displayName} user={0}")
    @MethodSource("params4SignUpFail")
    void signUpFail(User user) throws Exception {
        mvc.perform(post("/users/register").params(convertToMultiValueMap(user)))
                .andExpectAll(
                        content().string(EXISTENT_ID_MESSAGE),
                        status().isBadRequest());
    }
    static Stream<Arguments> params4SignUpFail() {
        return Stream.of(
                Arguments.of(User.builder("user1").name("name1").email("user1@gmail.com").build()),
                Arguments.of(User.builder("user2").name("name2").email("user2@gmail.com").build()),
                Arguments.of(User.builder("user3").name("name3").email("user3@gmail.com").build()),
                Arguments.of(User.builder("user4").name("name4").email("user4@gmail.com").build())
        );
    }
    private MultiValueMap<String, String> convertToMultiValueMap(Object obj) {
        Map<String, String> map = new ObjectMapper().convertValue(obj, new TypeReference<>() {});
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.setAll(map);

        return params;
    }

    @DisplayName("회원목록 페이지를 요청하면 사용자 목록을 출력한다.")
    @Test
    void getUserList() throws Exception {
        mvc.perform(get("/users"))
                .andExpectAll(
                        model().attributeExists("users"),
                        model().attribute("users", users),
                        content().contentTypeCompatibleWith(MediaType.TEXT_HTML),
                        content().encoding(StandardCharsets.UTF_8),
                        status().isOk()
                );
    }

    @DisplayName("회원프로필을 요청하면 해당하는 유저를 출력한다.")
    @ParameterizedTest(name ="{index} {displayName} user={0}")
    @MethodSource("params4SignUpFail")
    void getUserProfileSuccess(User user) throws Exception {
        String userId = user.getUserId();
        mvc.perform(get("/users/" + userId))
                .andExpectAll(
                        model().attributeExists("user"),
                        model().attribute("user", user),
                        content().contentTypeCompatibleWith(MediaType.TEXT_HTML),
                        content().encoding(StandardCharsets.UTF_8),
                        status().isOk()
                );
    }

    @DisplayName("등록되지 않은 회원프로필을 요청하면 BadRequest를 응답 받는다.")
    @ParameterizedTest(name ="{index} {displayName} user={0}")
    @MethodSource("params4SignUpSuccess")
    void getUserProfileFail(User user) throws Exception {
        String userId = user.getUserId();
        mvc.perform(get("/users/" + userId))
                .andExpectAll(
                        content().string(NON_EXISTENT_ID_MESSAGE),
                        status().isBadRequest()
                );
    }
}
