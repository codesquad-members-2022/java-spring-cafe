package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class User {

    private Long id;
    private String email;
    private String nickname;
    private String password;
    private LocalDateTime createdAt;

    public User(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public static User createUserRecord(Long id, User user) {
        user.id = id;
        user.createdAt = LocalDateTime.now();
        return user;
    }

    public Long getId() {
        return id;
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

    public boolean hasSameId(Long id) {
        return this.id.equals(id);
    }

    public boolean hasSameEmail(String email) {
        return this.email.equals(email);
    }
}
