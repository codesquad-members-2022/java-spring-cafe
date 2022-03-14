package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class User {

    private String email;
    private final String userId;
    private String password;
    private final LocalDateTime createdAt;

    public User(String email, String userId, String password) {
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean hasSameEmail(String email) {
        return this.email.equals(email);
    }

    public boolean hasSameUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean isCorrectPassword(String password) {
        return this.password.equals(password);
    }

    public User update(String newEmail, String newPassword) {
        this.email = newEmail;
        this.password = newPassword;
        return this;
    }
}
