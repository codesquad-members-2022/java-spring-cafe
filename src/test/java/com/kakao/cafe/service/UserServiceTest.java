package com.kakao.cafe.service;

import com.kakao.cafe.domain.user.JdbcUserRepository;
import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.web.dto.UserJoinDto;
import com.kakao.cafe.web.dto.UserUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"/schema.sql", "/test_data.sql"})
class UserServiceTest {
    UserService userService;
    JdbcUserRepository userRepository;
    @Autowired
    DataSource dataSource;

    @BeforeEach
    void setUp() {
        userRepository = new JdbcUserRepository(dataSource);
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("회원가입")
    void userJoin() {
        //given 준비
        UserJoinDto dto = new UserJoinDto("dto", "dto", "dto", "dto");
        //when 실행
        userService.userJoin(dto);
        User userDto = userRepository.findByUserId("dto");
        //then 검증
        assertThat(userDto.getName()).isEqualTo("dto");

    }

    @Test
    @DisplayName("회원 조회")
    void findUser() {
        //when 실행
        User userA = userService.findUser("test1");
        User userB = userService.findById(1L);
        //then 검증
        assertThat(userA.getName()).isEqualTo("name1");
        assertThat(userB.getName()).isEqualTo("name1");
    }
    @Test
    @DisplayName("회원 전체 조회")
    void findUsers() {
        //when 실행
        List<User> users = userService.findUsers();
        //then 검증
        assertThat(users.size()).isEqualTo(3);
    }
    @Test
    @DisplayName("회원 업데이트")
    void update() {
        //given 준비
        User user = userService.findById(1L);
        UserUpdateDto dto = new UserUpdateDto("updateName", "updateEmail");
        user.updateProfile(dto);
        //when 실행
        userService.userUpdate(user.getId(), user);
        User updateUser = userRepository.findByUserId("test1");
        //then 검증
        assertThat(updateUser.getName()).isEqualTo("updateName");
    }
}
