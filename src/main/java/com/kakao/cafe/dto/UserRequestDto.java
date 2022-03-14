package com.kakao.cafe.dto;

public class UserRequestDto {

    private final String name;
    private final String email;
    private final String password;

    public UserRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return name;
    }

    public String getUserEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
