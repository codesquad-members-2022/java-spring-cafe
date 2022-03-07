package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.User;

public class UserRegisterFormDto {

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public UserRegisterFormDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
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
        return new User.UserBuilder(userId,password)
            .setName(name)
            .setEmail(email)
            .build();
    }
}
