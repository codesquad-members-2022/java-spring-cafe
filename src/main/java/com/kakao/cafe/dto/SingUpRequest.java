package com.kakao.cafe.dto;

import com.kakao.cafe.domain.User;

public class SingUpRequest {

    private String userId;
    private String password;
    private String name;
    private String email;

    public SingUpRequest(String userId, String password, String name, String email) {
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
        return "SingUpRequest{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
