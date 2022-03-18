package com.kakao.cafe.dto;

import com.kakao.cafe.domain.User;

public class UserResponse {

    private Integer userNum;
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserResponse(Integer userNum, String userId, String password, String name,
        String email) {
        this.userNum = userNum;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static UserResponse from(User user) {
        return new UserResponse(
            user.getUserNum(),
            user.getUserId(),
            user.getPassword(),
            user.getName(),
            user.getEmail()
        );
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserResponse user = (UserResponse) o;

        return userId.equals(user.userId)
            && password.equals(user.password)
            && name.equals(user.name)
            && email.equals(user.email);
    }

    @Override
    public String toString() {
        return "UserResponse{" +
            "userNum=" + userNum +
            ", userId='" + userId + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
