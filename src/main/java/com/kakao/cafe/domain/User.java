package com.kakao.cafe.domain;

import com.kakao.cafe.dto.LoginForm;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InvalidRequestException;
import java.util.Objects;

public class User {

    private Integer userNum;
    private String userId;
    private String password;
    private String name;
    private String email;

    private User() {
    }

    public static class Builder {

        private Integer userNum;
        private String userId;
        private String password;
        private String name;
        private String email;

        public Builder userNum(int userNum) {
            this.userNum = userNum;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }

    private User(Builder builder) {
        userNum = builder.userNum;
        userId = builder.userId;
        password = builder.password;
        name = builder.name;
        email = builder.email;
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

    public User update(User user) {
        if (!userId.equals(user.userId) || !password.equals(user.password)) {
            throw new InvalidRequestException(ErrorCode.INCORRECT_USER);
        }

        this.name = user.name;
        this.email = user.email;

        return this;
    }

    public User authenticate(LoginForm loginForm) {
        if (!userId.equals(loginForm.getUserId()) || !password.equals(loginForm.getPassword())) {
            throw new InvalidRequestException(ErrorCode.INCORRECT_USER);
        }
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
