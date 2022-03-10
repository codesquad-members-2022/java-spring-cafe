package com.kakao.cafe.web.login.dto;

import javax.validation.constraints.NotBlank;

public class LoginDto {
    
    @NotBlank
    private String userId;
    @NotBlank
    private String password;

    public LoginDto() {
    }

    public LoginDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
