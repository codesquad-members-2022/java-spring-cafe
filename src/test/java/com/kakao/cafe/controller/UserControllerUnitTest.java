package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.dto.ModifiedUserParam;
import com.kakao.cafe.dto.NewUserParam;
import com.kakao.cafe.exception.user.DuplicateUserException;
import com.kakao.cafe.exception.user.NoSuchUserException;
import com.kakao.cafe.exception.user.UnMatchedPasswordException;
import com.kakao.cafe.service.UserService;
import com.kakao.cafe.util.Mapper;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Vector;
import java.util.stream.Stream;

import static com.kakao.cafe.message.UserDomainMessage.*;
import static com.kakao.cafe.util.Convertor.convertToMultiValueMap;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerUnitTest {

    final static int EXISTING_USERS_COUNT = 4;

    @Autowired
    MockMvc mvc;

    @MockBean
    UserService service;

    static List<User> users = new Vector<>();

    Mapper<User> userMapper = new Mapper<>();

    @BeforeAll
    static void init() {
        for (int i = 0; i < EXISTING_USERS_COUNT; ++i) {
            users.add(new User(-1, "user" + (i + 1), "1234", "name" + (i + 1),
                    "user" + (i + 1) + "@gmail.com"));
        }
    }

    @DisplayName("미등록 사용자가 회원가입을 요청하면 사용자 추가를 완료한 후 사용자 목록 페이지로 이동한다.")
    @ParameterizedTest(name = "{index} {displayName} user={0}")
    @MethodSource("params4SignUpSuccess")
    void signUpSuccess(NewUserParam newUserParam) throws Exception {
        given(service.add(newUserParam)).willReturn(userMapper.convertToDomain(newUserParam, User.class));

        mvc.perform(post("/users/register").params(convertToMultiValueMap(newUserParam)))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );

        verify(service).add(ArgumentMatchers.refEq(newUserParam));
    }

    static Stream<Arguments> params4SignUpSuccess() {
        return Stream.of(
                Arguments.of(new NewUserParam("user5", "1234", "name5", "user5@gmail.com")),
                Arguments.of(new NewUserParam("user6", "1234", "name6", "user6@gmail.com")),
                Arguments.of(new NewUserParam("user7", "1234", "name7", "user7@gmail.com")),
                Arguments.of(new NewUserParam("user8", "1234", "name8", "user8@gmail.com"))
        );
    }

    @DisplayName("등록된 사용자가 회원가입을 요청하면 DuplicateUserException 이 발생한다.")
    @ParameterizedTest(name = "{index} {displayName} user={0}")
    @MethodSource("params4SignUpFail")
    void signUpFail(NewUserParam newUserParam) throws Exception {
        given(service.add(ArgumentMatchers.refEq(newUserParam))).willThrow(new DuplicateUserException(HttpStatus.OK, DUPLICATE_USER_MESSAGE));

        mvc.perform(post("/users/register").params(convertToMultiValueMap(newUserParam)))
                .andExpectAll(
                        content().string(DUPLICATE_USER_MESSAGE),
                        status().isOk())
                .andDo(print());

        verify(service).add(ArgumentMatchers.refEq(newUserParam));
    }

    static Stream<Arguments> params4SignUpFail() {
        return Stream.of(
                Arguments.of(new NewUserParam("user1", "1234", "name1", "user1@gmail.com")),
                Arguments.of(new NewUserParam("user2", "1234", "name2", "user2@gmail.com")),
                Arguments.of(new NewUserParam("user3", "1234", "name3", "user3@gmail.com")),
                Arguments.of(new NewUserParam("user4", "1234", "name4", "user4@gmail.com"))
        );
    }

    @DisplayName("회원목록 페이지를 요청하면 사용자 목록을 출력한다.")
    @Test
    void getUsers() throws Exception {
        given(service.searchAll()).willReturn(users);

        mvc.perform(get("/users"))
                .andExpectAll(
                        model().attributeExists("users"),
                        model().attribute("users", users),
                        content().contentTypeCompatibleWith(MediaType.TEXT_HTML),
                        content().encoding(StandardCharsets.UTF_8),
                        status().isOk()
                );

        verify(service).searchAll();
    }

    @DisplayName("회원프로필을 요청하면 해당하는 유저를 출력한다.")
    @ParameterizedTest(name = "{index} {displayName} user={0}")
    @MethodSource("params4SignUpFail")
    void getUserProfileSuccess(NewUserParam newUserParam) throws Exception {
        User user = userMapper.convertToDomain(newUserParam, User.class);
        String userId = user.getUserId();
        given(service.search(userId)).willReturn(user);

        mvc.perform(get("/users/" + userId))
                .andExpectAll(
                        model().attributeExists("user"),
                        model().attribute("user", user),
                        content().contentTypeCompatibleWith(MediaType.TEXT_HTML),
                        content().encoding(StandardCharsets.UTF_8),
                        status().isOk()
                );

        verify(service).search(userId);
    }

    @DisplayName("등록되지 않은 회원프로필을 요청하면 BadRequest를 응답 받는다.")
    @ParameterizedTest(name = "{index} {displayName} user={0}")
    @MethodSource("params4SignUpSuccess")
    void getUserProfileFail(NewUserParam newUserParam) throws Exception {
        User user = userMapper.convertToDomain(newUserParam, User.class);
        String userId = user.getUserId();

        given(service.search(userId)).willThrow(new NoSuchUserException(HttpStatus.OK, NO_SUCH_USER_MESSAGE));
        mvc.perform(get("/users/" + userId))
                .andExpectAll(
                        content().string(NO_SUCH_USER_MESSAGE),
                        status().isOk()
                );

        verify(service).search(userId);
    }

    @DisplayName("회원정보 수정 요청이 들어오면 비밀번호 일치 여부를 확인 후 일치하면 반영하고 사용자 목록을 출력한다.")
    @ParameterizedTest(name = "{index} {displayName} user={0}")
    @MethodSource("params4modifiedProfileSuccess")
    void modifyProfileSuccess(ModifiedUserParam modifiedUserParam) throws Exception {
        modifiedUserParam.switchPassword();
        User user = userMapper.convertToDomain(modifiedUserParam, User.class);
        String userId = user.getUserId();

        given(service.update(modifiedUserParam)).willReturn(user);
        mvc.perform(put("/users/" + userId + "/update").params(convertToMultiValueMap(modifiedUserParam)))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );

        verify(service).update(ArgumentMatchers.refEq(modifiedUserParam));
    }

    static Stream<Arguments> params4modifiedProfileSuccess() {
        return Stream.of(
                Arguments.of(new ModifiedUserParam(1, "user1", "1234", "1234",
                        "4321", "name1", "user1@gmail.com")),
                Arguments.of(new ModifiedUserParam(2, "user2", "1234", "1234",
                        "4321", "name2", "user2@gmail.com")),
                Arguments.of(new ModifiedUserParam(3, "user3", "1234", "1234",
                        "4321", "name3", "user3@gmail.com")),
                Arguments.of(new ModifiedUserParam(4, "user4", "1234", "1234",
                        "4321", "name4", "user4@gmail.com"))
        );
    }

    @DisplayName("회원정보 수정 요청이 들어오면 비밀번호 일치 여부를 확인 후 일치하지 않으면 UnMatchedPasswordException 을 발생시킨다.")
    @ParameterizedTest(name = "{index} {displayName} user={0}")
    @MethodSource("params4modifiedProfileFail")
    void modifyProfileFail(ModifiedUserParam modifiedUserParam) throws Exception {
        modifiedUserParam.switchPassword();
        User user = userMapper.convertToDomain(modifiedUserParam, User.class);

        String userId = user.getUserId();

        given(service.update(ArgumentMatchers.refEq(modifiedUserParam))).willThrow(new UnMatchedPasswordException(HttpStatus.OK, UNMATCHED_PASSWORD_MESSAGE));
        mvc.perform(put("/users/" + userId + "/update").params(convertToMultiValueMap(modifiedUserParam)))
                .andExpectAll(
                        content().string(UNMATCHED_PASSWORD_MESSAGE),
                        status().isOk()
                );

        verify(service).update(ArgumentMatchers.refEq(modifiedUserParam));
    }

    static Stream<Arguments> params4modifiedProfileFail() {
        return Stream.of(
                Arguments.of(new ModifiedUserParam(1, "user1", "1234", "4321",
                        "4321", "name1", "user1@gmail.com")),
                Arguments.of(new ModifiedUserParam(2, "user2", "1234", "4321",
                        "4321", "name2", "user2@gmail.com")),
                Arguments.of(new ModifiedUserParam(3, "user3", "1234", "4321",
                        "4321", "name3", "user3@gmail.com")),
                Arguments.of(new ModifiedUserParam(4, "user4", "1234", "4321",
                        "4321", "name4", "user4@gmail.com"))
        );
    }
}
