package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.user.User;

public class UserResponseDto {

    private final String userId;
    private final String name;
    private final String email;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public boolean hasSameId(String userId) {
        return this.userId.equals(userId);
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
