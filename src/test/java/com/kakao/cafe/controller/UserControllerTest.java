package com.kakao.cafe.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.SingUpRequest;
import com.kakao.cafe.repository.Repository;
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
    Repository<User, String> repository;

    static List<User> users;

    @BeforeAll
    static void init() {
        users = new ArrayList<>();
        for (int i = 0; i < EXISTING_USERS_COUNT; ++i) {
            users.add(
                    new User((i + 1),"user" + (i + 1),
                            "1234",
                            "name" + (i + 1),
                            "user" + (i + 1) + "@gmail.com")
            );
        }
    }

    @BeforeEach
    void setUp() {
        repository.clear();
        users.forEach(repository::save);
    }

    @DisplayName("미등록 사용자가 회원가입을 요청하면 사용자 추가를 완료한 후 사용자 목록 페이지로 이동한다.")
    @ParameterizedTest(name ="{index} {displayName} user={0}")
    @MethodSource("params4SignUpSuccess")
    void signUpSuccess(SingUpRequest singUpRequest) throws Exception {
        mvc.perform(post("/users/register").params(convertToMultiValueMap(singUpRequest)))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );
    }
    static Stream<Arguments> params4SignUpSuccess() {
        return Stream.of(
                Arguments.of(new SingUpRequest("user5", "1234","name5", "user5@gmail.com")),
                Arguments.of(new SingUpRequest("user6", "1234","name6", "user6@gmail.com")),
                Arguments.of(new SingUpRequest("user7", "1234","name7", "user7@gmail.com")),
                Arguments.of(new SingUpRequest("user8", "1234","name8", "user8@gmail.com"))
        );
    }

    @DisplayName("등록된 사용자가 회원가입을 요청하면 BadRequest를 응답 받는다.")
    @ParameterizedTest(name ="{index} {displayName} user={0}")
    @MethodSource("params4SignUpFail")
    void signUpFail(SingUpRequest singUpRequest) throws Exception {
        mvc.perform(post("/users/register").params(convertToMultiValueMap(singUpRequest)))
                .andExpectAll(
                        content().string(EXISTENT_ID_MESSAGE),
                        status().isBadRequest());
    }
    static Stream<Arguments> params4SignUpFail() {
        return Stream.of(
                Arguments.of(new SingUpRequest("user1", "1234","name1", "user1@gmail.com")),
                Arguments.of(new SingUpRequest("user2", "1234","name2", "user2@gmail.com")),
                Arguments.of(new SingUpRequest("user3", "1234","name3", "user3@gmail.com")),
                Arguments.of(new SingUpRequest("user4", "1234","name4", "user4@gmail.com"))
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
    void getUserProfileSuccess(SingUpRequest singUpRequest) throws Exception {
        User user = singUpRequest.convertToUser();
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
    void getUserProfileFail(SingUpRequest singUpRequest) throws Exception {
        User user = singUpRequest.convertToUser();
        String userId = user.getUserId();
        mvc.perform(get("/users/" + userId))
                .andExpectAll(
                        content().string(NON_EXISTENT_ID_MESSAGE),
                        status().isBadRequest()
                );
    }
}
