package com.kakao.cafe.domain;

import com.kakao.cafe.controller.UserForm;

public class User {

    private String userId;
    private String name;
    private String email;

    public User(UserForm form) {
        this.name = form.getName();
        this.email = form.getEmail();
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
