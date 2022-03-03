package com.kakao.cafe.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kakao.cafe.domain.user.User;
import com.kakao.cafe.domain.user.dto.UserDto;
import com.kakao.cafe.domain.user.dto.UserProfileDto;
import com.kakao.cafe.repository.UserRepository;

// @ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class) // MockitoExtension을 포함하는 개념
class UserServiceTest {

    @InjectMocks
    UserService userService; // @Mock이 여기로 주입됨

    @Mock
    UserRepository userRepository; // 주입대상

    User user1;
    User user2;

    @BeforeEach
    void init() {
        user1 = new User("lucid", "1234", "lee", "lee@naver.com");
        user2 = new User("tesla", "0000", "elon", "elon@tesla.com");
    }

    @DisplayName("findAllUser를 호출하면 List<UserDto>가 반환된다.")
    @Test
    void test_find_All_User() {
        // given
        given(userRepository.findAll())
            .willReturn(List.of(user1, user2));

        // when
        List<UserDto> allUser = userService.findAllUser();

        // then
        assertThat(allUser.size()).isEqualTo(2);
        allUser.forEach(userDto -> assertThat(userDto).isInstanceOf(UserDto.class));
    }
    

    @DisplayName("userId를 사용하여 UserProfileDto를 반환받는다.")
    @Test
    void test_find_User_By_User_Id() {
        // given
        given(userRepository.findByUserId("lucid"))
            .willReturn(Optional.of(user1));

        // when
        UserProfileDto userProfileDto = userService.findUserByUserId("lucid");

        // then
        assertThat(userProfileDto.getEmail()).isEqualTo("lee@naver.com");
    }

}