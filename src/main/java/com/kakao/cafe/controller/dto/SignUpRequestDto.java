package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.User;

public class SignUpRequestDto {

    private String email;
    private String userId;
    private String password;

    public SignUpRequestDto(String email, String userId, String password) {
        this.email = email;
        this.userId = userId;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public User toEntity() {
        return new User(email, userId, password);
    }
}
