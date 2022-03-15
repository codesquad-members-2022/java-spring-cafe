package com.kakao.cafe.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.NewUserParam;
import com.kakao.cafe.repository.DomainRepository;
import com.kakao.cafe.util.Mapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Stream;

import static com.kakao.cafe.message.UserDomainMessage.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Deprecated
@JdbcTest
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    final static int EXISTING_USERS_COUNT = 4;

    @Autowired
    MockMvc mvc;

    @Autowired
    DomainRepository<User, String> repository;

    Mapper<User> userMapper = new Mapper<>();

    static List<User> users = new Vector<>();;

    @BeforeAll
    static void init() {
        for (int i = 0; i < EXISTING_USERS_COUNT; ++i) {
            users.add(new User((i + 1),"user" + (i + 1), "1234", "name" + (i + 1),
                            "user" + (i + 1) + "@gmail.com"));
        }
    }

    @BeforeEach
    void setUp() {
//        repository.clear();
        users.forEach(repository::save);
    }

    @DisplayName("미등록 사용자가 회원가입을 요청하면 사용자 추가를 완료한 후 사용자 목록 페이지로 이동한다.")
    @ParameterizedTest(name ="{index} {displayName} user={0}")
    @MethodSource("params4SignUpSuccess")
    void signUpSuccess(NewUserParam newUserParam) throws Exception {
        mvc.perform(post("/users/register").params(convertToMultiValueMap(newUserParam)))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );
    }
    static Stream<Arguments> params4SignUpSuccess() {
        return Stream.of(
                Arguments.of(new NewUserParam("user5", "1234","name5", "user5@gmail.com")),
                Arguments.of(new NewUserParam("user6", "1234","name6", "user6@gmail.com")),
                Arguments.of(new NewUserParam("user7", "1234","name7", "user7@gmail.com")),
                Arguments.of(new NewUserParam("user8", "1234","name8", "user8@gmail.com"))
        );
    }

    @DisplayName("등록된 사용자가 회원가입을 요청하면 Bad Request 를 응답 받는다.")
    @ParameterizedTest(name ="{index} {displayName} user={0}")
    @MethodSource("params4SignUpFail")
    void signUpFail(NewUserParam newUserParam) throws Exception {
        mvc.perform(post("/users/register").params(convertToMultiValueMap(newUserParam)))
                .andExpectAll(
                        content().string(DUPLICATE_USER_MESSAGE),
                        status().isBadRequest());
    }
    static Stream<Arguments> params4SignUpFail() {
        return Stream.of(
                Arguments.of(new NewUserParam("user1", "1234","name1", "user1@gmail.com")),
                Arguments.of(new NewUserParam("user2", "1234","name2", "user2@gmail.com")),
                Arguments.of(new NewUserParam("user3", "1234","name3", "user3@gmail.com")),
                Arguments.of(new NewUserParam("user4", "1234","name4", "user4@gmail.com"))
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
    void getUsers() throws Exception {
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
    void getUserProfileSuccess(NewUserParam newUserParam) throws Exception {
        User user = userMapper.convertToDomain(newUserParam, User.class);
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

    @DisplayName("등록되지 않은 회원프로필을 요청하면 Bad Request 를 응답 받는다.")
    @ParameterizedTest(name ="{index} {displayName} user={0}")
    @MethodSource("params4SignUpSuccess")
    void getUserProfileFail(NewUserParam newUserParam) throws Exception {
        User user = userMapper.convertToDomain(newUserParam, User.class);
        String userId = user.getUserId();
        mvc.perform(get("/users/" + userId))
                .andExpectAll(
                        content().string(NO_SUCH_USER_MESSAGE),
                        status().isBadRequest()
                );
    }
}
