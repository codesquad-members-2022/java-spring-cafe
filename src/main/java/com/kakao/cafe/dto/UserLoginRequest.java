package com.kakao.cafe.dto;

public class UserLoginRequest {

    private String userId;
    private String password;

    private UserLoginRequest() {
    }

    public UserLoginRequest(String userId, String password) {
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
