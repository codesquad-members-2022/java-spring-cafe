package com.kakao.cafe.web.dto;

public class UserUpdateDto {
    private String password;
    private String name;
    private String email;

    public UserUpdateDto(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
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
