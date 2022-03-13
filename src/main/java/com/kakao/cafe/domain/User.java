package com.kakao.cafe.domain;

import java.time.LocalDateTime;
import java.util.Optional;

public class User {

    private String userId;
    private String password;
    private String name;
    private String email;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    private User() {
    }

    public User(String userId, String password, String name, String email, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.createdTime = Optional.ofNullable(createdTime).orElse(LocalDateTime.now());
        this.updatedTime = Optional.ofNullable(updatedTime).orElse(LocalDateTime.now());
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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
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

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
