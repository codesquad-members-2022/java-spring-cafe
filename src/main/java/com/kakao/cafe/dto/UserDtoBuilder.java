package com.kakao.cafe.dto;

public interface UserDtoBuilder {

    UserDtoBuilder userEmail(String email);

    UserDtoBuilder userName(String name);

    UserDtoBuilder userId(String userId);

    UserResponseDto getUserDto();
}
