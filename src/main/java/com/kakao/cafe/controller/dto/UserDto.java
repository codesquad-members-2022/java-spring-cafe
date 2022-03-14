package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @Email
    private String email;

    public UserDto(User user) {
        userId = user.getUserId();
        password = user.getPassword();
        name = user.getName();
        email = user.getEmail();
    }

    public User toEntity() {
        return new User(userId, password, name, email);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

}
