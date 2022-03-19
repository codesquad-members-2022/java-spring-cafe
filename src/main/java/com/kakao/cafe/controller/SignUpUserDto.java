package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;

public class SignUpUserDto {

    private String userId;
    private String password;
    private String name;
    private String email;

    public SignUpUserDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public User toEntity() {
        return new User(userId, password, name, email);
    }

}
