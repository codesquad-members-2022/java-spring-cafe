package com.kakao.cafe.domain.dto;

import com.kakao.cafe.domain.User;

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
    @NotBlank
    private String email;

    private int id;

    public UserForm(String userId, String name, String password, String email) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User createUser() {
        return new User(userId, name, password, email);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
