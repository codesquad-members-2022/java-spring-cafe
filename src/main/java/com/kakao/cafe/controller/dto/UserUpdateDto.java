package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserUpdateDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @Email
    private String email;

    public UserUpdateDto() {
    }

    public UserUpdateDto(User loginUser) {
        this.userId = loginUser.getUserId();
        this.name = loginUser.getName();
        this.email = loginUser.getEmail();
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User toEntity() {
        return new User(userId, password, name, email);
    }
}
