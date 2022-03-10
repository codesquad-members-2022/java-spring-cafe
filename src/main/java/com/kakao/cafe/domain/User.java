package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class User {

    private String email;
    private String nickname;
    private String password;
    private LocalDateTime createdAt;

    public User(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean hasSameEmail(String email) {
        return this.email.equals(email);
    }
}
