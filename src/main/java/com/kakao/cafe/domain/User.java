package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class User {

    private final Integer id;
    private final String userId;
    private String email;
    private String password;
    private final LocalDateTime createdAt;

    public User(String email, String userId, String password) {
        this.id = null;
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public User(int id, User user) {
        this.id = id;
        this.email = user.email;
        this.userId = user.userId;
        this.password = user.password;
        this.createdAt = user.createdAt;
    }

    public User(Integer id, String email, String userId, String password,
        LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
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

    public boolean isNewUser() {
        return this.id == null;
    }

    public User update(String newEmail, String newPassword) {
        this.email = newEmail;
        this.password = newPassword;
        return this;
    }
}
