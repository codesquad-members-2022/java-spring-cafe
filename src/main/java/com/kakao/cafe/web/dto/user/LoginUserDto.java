package com.kakao.cafe.web.dto.user;

import javax.validation.constraints.NotBlank;

public class LoginUserDto {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;

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
