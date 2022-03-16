package com.kakao.cafe.controller;

import com.kakao.cafe.domain.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserForm {
    private String email;
    private String userId;
    private String password;
    private LocalDateTime createdAt;

    private UserForm(String email, String userId, String password, LocalDateTime createdAt) {
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.createdAt = createdAt;
    }

    public static UserForm from(User user) {
        return new UserForm(user.getEmail(), user.getUserId(), user.getPassword(), user.getCreatedAt());
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserForm userForm = (UserForm) o;
        return Objects.equals(email, userForm.email) && Objects.equals(userId, userForm.userId) && Objects.equals(password, userForm.password) && Objects.equals(createdAt, userForm.createdAt);
    }
}
