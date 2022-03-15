package com.kakao.cafe.entity;

import com.kakao.cafe.dto.UserRequestDto;
import com.kakao.cafe.dto.UserResponseDto;

public class User {

    private final String userId;
    private String email;
    private String name;
    private String password;

    public User(String email, String userId, String name, String password) {
        this.email = email;
        this.userId = userId;
        this.name = name;
        this.password = password;
    }

    public boolean isSameUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean isSameUserEmail(String userEmail) {
        return this.email.equals(userEmail);
    }

    public boolean isSameUserPassword(UserRequestDto requestDto) {
        return this.password.equals(requestDto.getPassword());
    }

    public User updateUser(UserRequestDto requestDto) {
        this.email = requestDto.getUserEmail();
        this.name = requestDto.getUserName();
        this.password = requestDto.getPassword();
        return this;
    }

    public UserResponseDto of() {
        UserResponseDto dto = new UserResponseDto();
        return dto.userName(name)
                .userEmail(email)
                .userId(userId)
                .getUserDto();
    }
}
