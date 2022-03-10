package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class User {
    private String email;
    private String userId;
    private String password;
    private LocalDateTime createdAt;

    public LocalDateTime getCreatedDate() {
        return createdAt;
    }

    public void setCreatedDate(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
