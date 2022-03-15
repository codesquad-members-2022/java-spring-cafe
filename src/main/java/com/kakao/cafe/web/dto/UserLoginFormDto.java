package com.kakao.cafe.web.dto;

public class UserLoginFormDto {

    private String userId;
    private String password;

    public UserLoginFormDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserLoginFormDto{" +
            "userId='" + userId + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}
