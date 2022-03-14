package com.kakao.cafe.controller.dto;

public class UserUpdateRequestDto {

    private String userId;
    private String newEmail;
    private String password;
    private String newPassword;

    public UserUpdateRequestDto(String userId, String newEmail, String password,
        String newPassword) {
        this.userId = userId;
        this.newEmail = newEmail;
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getUserId() {
        return userId;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
