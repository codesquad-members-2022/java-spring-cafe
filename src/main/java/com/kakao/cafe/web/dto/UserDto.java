package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.user.User;

public class UserDto {

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public UserDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User toEntity() {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        user.setName(name);
        user.setEmail(email);

        return user;
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

}
