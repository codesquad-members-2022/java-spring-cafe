package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.user.User;

public class UserJoinRequestDto {
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserJoinRequestDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User toEntity() {
        return new User(userId, password, name, email);
    }
}
