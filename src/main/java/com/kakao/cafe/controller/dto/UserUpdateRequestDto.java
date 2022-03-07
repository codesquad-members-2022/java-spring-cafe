package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.user.User;

public class UserUpdateRequestDto {
    private String userId;
    private String password;
    private String changedPassword;
    private String name;
    private String email;

    public UserUpdateRequestDto(String userId, String password, String changedPassword, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.changedPassword = changedPassword;
        this.name = name;
        this.email = email;
    }

    public User toEntity() {
        return new User(userId, changedPassword, name, email);
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getChangedPassword() {
        return changedPassword;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
