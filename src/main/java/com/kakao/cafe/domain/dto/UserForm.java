package com.kakao.cafe.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserForm {
    private Long id;

    @NotBlank
    private String userId;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @Email
    private String email;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
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
