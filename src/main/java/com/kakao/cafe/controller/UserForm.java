package com.kakao.cafe.controller;

public class UserForm {
    private String userId;
    private String name;
    private String email;

    public UserForm(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
