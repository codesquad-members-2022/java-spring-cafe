package com.kakao.cafe.domain;

import com.kakao.cafe.controller.UserForm;

import java.time.LocalDateTime;

public class User {
    private String email;
    private String userId;
    private String password;
    private LocalDateTime createdAt;

    public User (String email, String userId, String password){
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedDate() {
        return createdAt;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public boolean isSameEmail(String email){
        return this.email.equals(email);
    }

    public boolean isSameUserId(String userId){
        return this.userId.equals(userId);
    }
}
