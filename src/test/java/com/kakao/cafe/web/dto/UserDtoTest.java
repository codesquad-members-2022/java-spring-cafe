package com.kakao.cafe.web.dto;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class UserDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("userDto userId,password,name,email에 null이 담기면 안된다.")
    void userDto_nullTest() {
        UserDto userDtoNull = new UserDto(null, null, null, null);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDtoNull);

        assertThat(violations.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("userDto userId,password,name,email가 비어있으면 안된다.")
    void userDto_emptyTest() {
        UserDto userDtoEmpty = new UserDto("", "", "", "");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDtoEmpty);

        assertThat(violations.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("userDto userId,password,name,email에 공백이 담기면 안된다.")
    void userDto_blankTest() {
        UserDto userDtoEmpty = new UserDto(" ", " ", " ", " ");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDtoEmpty);

        assertThat(violations.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("userDto email은 email 정규식을 지켜야한다.")
    void userDto_emailRegexTest() {
        UserDto userDtoWrongEmail = new UserDto("ron2", "1234", "ron2", "failEmail");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDtoWrongEmail);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("userDto 검증 통과 테스트")
    void userDto_successTest() {
        UserDto userDtoSuccess = new UserDto("ron2", "1234", "ron2", "ron2@gmail.com");
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDtoSuccess);

        assertThat(violations.size()).isEqualTo(0);
    }

}
