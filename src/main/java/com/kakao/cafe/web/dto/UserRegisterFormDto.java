package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.User;

public class UserRegisterFormDto {

    private String userId;
    private String password;
    private String name;
    private String email;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserSignUpFormDto{" +
            "userId='" + userId + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
    }

    public User toEntity() {
        return new User.UserBuilder().setUserId(userId)
            .setPassword(password)
            .setName(name)
            .setEmail(email)
            .build();
    }
}
