package com.kakao.cafe.domain;

import com.kakao.cafe.exception.CustomException;
import com.kakao.cafe.exception.ErrorCode;

public class User {

    private Integer userNum;
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User(User other) {
        this.userNum = other.userNum;
        this.userId = other.userId;
        this.password = other.password;
        this.name = other.name;
        this.email = other.email;
    }

    public Integer getUserNum() {
        return userNum;
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

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User update(User user) {
        if (!userId.equals(user.userId) || !password.equals(user.password)) {
            throw new CustomException(ErrorCode.INCORRECT_USER);
        }

        this.name = user.name;
        this.email = user.email;

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return userId.equals(user.userId);
    }

    @Override
    public String toString() {
        return "User{" +
            "userNum=" + userNum +
            ", userId='" + userId + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
    }

}
