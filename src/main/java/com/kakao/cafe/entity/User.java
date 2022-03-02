package com.kakao.cafe.entity;

import java.time.LocalDate;

public class User {

    private final String email;
    private final String userId;
    private final String name;
    private final String password;
    private final LocalDate userRegistrationDate;
    private Long id;

    public User(String email, String nickname, String name, String password) {
        this.email = email;
        this.userId = nickname;
        this.name = name;
        this.password = password;
        this.userRegistrationDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUserId() {
        return this.userId;
    }

    public LocalDate getUserRegistrationDate() {
        return this.userRegistrationDate;
    }
}
