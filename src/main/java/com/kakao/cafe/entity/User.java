package com.kakao.cafe.entity;

import com.kakao.cafe.dto.UserResponseDto;

public class User {

    private final String email;
    private final String userId;
    private final String name;
    private final String password;

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

    public UserResponseDto of() {
        UserResponseDto dto = new UserResponseDto();
        return dto.userName(name)
                .userEmail(email)
                .userId(userId)
                .getUserDto();
    }
}
