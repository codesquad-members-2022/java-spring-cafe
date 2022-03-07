package com.kakao.cafe.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserForm {
    @NotBlank
    private String userId;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @Email
    private String email;

    public UserForm(@NotBlank String userId, @NotBlank String name, @NotBlank String password, @Email String email) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
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
}
