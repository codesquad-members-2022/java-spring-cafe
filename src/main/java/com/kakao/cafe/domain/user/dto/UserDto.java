package com.kakao.cafe.domain.user.dto;

public class UserDto {
    private int number;
    private String userId;
    private String name;
    private String email;

    public UserDto(int number, String userId, String name, String email) {
        this.number = number;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
