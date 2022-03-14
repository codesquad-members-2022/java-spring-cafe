package com.kakao.cafe.web.dto;

import javax.validation.constraints.NotBlank;

public class LoginDto {

    @NotBlank
    private final String userId;
    @NotBlank
    private final String password;

    public LoginDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
