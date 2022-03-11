package com.kakao.cafe.dto;

import com.kakao.cafe.domain.User;

public class UserRequestDto {

    private String userId;
    private String password;
    private String name;
    private String email;

    public UserRequestDto(String userId, String password, String name, String email) {
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
