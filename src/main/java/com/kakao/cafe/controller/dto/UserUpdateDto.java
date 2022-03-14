package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserUpdateDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String name;

    @Email
    private String email;

    public UserUpdateDto() {
    }

    public UserUpdateDto(User user) {
        userId = user.getUserId();
        name = user.getName();
        email = user.getEmail();
    }

    public User toEntity() {
        return new User(userId, newPassword, name, email);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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
