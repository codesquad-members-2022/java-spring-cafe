package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class User {

    private String userId;
    private String password;
    private String name;
    private String email;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.created_date = LocalDateTime.now();
        this.updated_date = LocalDateTime.now();
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

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public LocalDateTime getUpdated_date() {
        return updated_date;
    }

    public void update(String newPassword, String newName, String newEmail) {
        this.password = newPassword;
        this.name = newName;
        this.email = newEmail;
    }

    public boolean isCorrectUser(String userId) {
        return this.userId.equals(userId);
    }

    public boolean isCorrectPassword(String password) {
        return this.password.equals(password);
    }
}
