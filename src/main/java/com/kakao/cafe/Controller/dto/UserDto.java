package com.kakao.cafe.Controller.dto;

import com.kakao.cafe.domain.User;

public class UserDto {

    private String userId;
    private String name;
    private String email;

    public UserDto(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public static UserDto from(User user) {
        return new UserDto(user.getUserId(),
                user.getName(),
                user.getEmail());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
