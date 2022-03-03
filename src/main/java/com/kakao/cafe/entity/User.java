package com.kakao.cafe.entity;

public class User {

    private final String email;
    private final String userId;
    private final String name;
    private final String password;

    public User(String email, String userId, String name, String password) {
        this.email = email;
        this.userId = userId;
        this.name = name;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
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
                '}';
    }
}
