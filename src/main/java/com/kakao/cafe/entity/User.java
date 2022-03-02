package com.kakao.cafe.entity;

import java.time.LocalDate;

public class User {

    private final String email;
    private final String userId;
    private final String name;
    private final String password;
    private final LocalDate userRegistrationDate;
    private Long id;

    public User(String email, String userId, String name, String password) {
        this.email = email;
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.userRegistrationDate = LocalDate.now();
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSameUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean isSameUserEmail(String userEmail) {
        return this.email.equals(userEmail);
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", userRegistrationDate=" + userRegistrationDate +
                ", id=" + id +
                '}';
    }
}
