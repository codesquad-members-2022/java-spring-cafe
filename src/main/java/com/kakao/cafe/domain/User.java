package com.kakao.cafe.domain;

import com.kakao.cafe.dto.UserResponseDto;

public class User {

    private String userId;
    private String password;
    private String name;
    private String email;

    private User() { }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
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

    public UserResponseDto convertToDto() {
        return new UserResponseDto(userId, name, email);
    }

    public void update(User user) {
        name = user.getName();
        email = user.getEmail();
    }

    public boolean hasSameUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean hasSamePassword(String password) {
        return this.password.equals(password);
    }
}
