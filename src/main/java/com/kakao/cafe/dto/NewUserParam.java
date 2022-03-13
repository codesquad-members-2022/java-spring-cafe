package com.kakao.cafe.dto;

import com.kakao.cafe.domain.User;

public class NewUserParam {

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public NewUserParam(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
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

    public User convertToUser() {
        return new User(-1, userId, password, name, email);
    }

    @Override
    public String toString() {
        return "NewUserParam{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
