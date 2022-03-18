package com.kakao.cafe.domain;

import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InvalidRequestException;
import java.util.Objects;

public class User {

    private Integer userNum;
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this(null, userId, password, name, email);
    }

    public User(Integer userNum, String userId, String password, String name, String email) {
        this.userNum = userNum;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
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

    public User update(String name, String email) {
        this.name = name;
        this.email = email;
        return this;
    }

    public void checkPassword(String password) {
        if (!this.password.equals(password)) {
            throw new InvalidRequestException(ErrorCode.INCORRECT_USER);
        }
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

        return Objects.equals(userId, user.userId);
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
