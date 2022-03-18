package com.kakao.cafe.controller.dto;

public class UserLoginRequestDto {
    private String userId;
    private String password;

    public UserLoginRequestDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
