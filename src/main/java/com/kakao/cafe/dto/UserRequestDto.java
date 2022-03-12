package com.kakao.cafe.dto;

import com.kakao.cafe.domain.User;

public class UserRequestDto {

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public UserRequestDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User convertToDomain() {
        return new User(userId, password, name, email);
    }

    @Override
    public String toString() {
        return "UserDto{" +
            "userId='" + userId + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
