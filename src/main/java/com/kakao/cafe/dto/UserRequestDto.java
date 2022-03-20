package com.kakao.cafe.dto;

import com.kakao.cafe.entity.User;

public class UserRequestDto {

    private final String userId;
    private final String name;
    private final String email;
    private final String password;

    public UserRequestDto(String userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }

    public User of() {
        return new User(this.email, this.userId, this.name, this.password);
    }
}
