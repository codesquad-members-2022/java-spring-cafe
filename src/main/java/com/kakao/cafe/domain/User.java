package com.kakao.cafe.domain;

import com.kakao.cafe.controller.UserForm;

public class User {

    private String userId;
    private String password;
    private String name;
    private String email;

    public User(UserForm form) {
        userId = form.getUserId();
        password = form.getPassword();
        name = form.getName();
        email = form.getEmail();
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
