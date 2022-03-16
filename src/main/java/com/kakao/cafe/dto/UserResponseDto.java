package com.kakao.cafe.dto;

public class UserResponseDto {

    private final String userId;
    private final String name;
    private final String email;

    public UserResponseDto(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
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

    public boolean hasSameUserId(String userId) {
        return this.userId.equals(userId);
    }
}
