package com.kakao.cafe.domain.user.dto;

public class UserProfileDto {
    private String name;
    private String email;

    public UserProfileDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
