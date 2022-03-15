package com.kakao.cafe.domain.dto;

import com.kakao.cafe.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UpdateUserForm {
    @NotBlank
    private String userId;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String newPassword;


    public UpdateUserForm(String userId, String name, String password, String email, String newPassword) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.newPassword = newPassword;
    }

    public User createUser(String newPassword) {
        return new User(userId, name, newPassword, email);
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
