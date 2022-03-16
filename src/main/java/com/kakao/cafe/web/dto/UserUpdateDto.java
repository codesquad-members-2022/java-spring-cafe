package com.kakao.cafe.web.dto;

public class UserUpdateDto {
    private String name;
    private String email;

    public UserUpdateDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
