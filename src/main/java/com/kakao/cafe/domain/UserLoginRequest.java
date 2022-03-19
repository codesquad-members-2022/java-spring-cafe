package com.kakao.cafe.domain;

public class UserLoginRequest {

    private String userId;
    private String password;

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
