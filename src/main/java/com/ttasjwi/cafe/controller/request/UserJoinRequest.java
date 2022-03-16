package com.ttasjwi.cafe.controller.request;

import com.ttasjwi.cafe.domain.user.User;

import java.time.LocalDate;

public class UserJoinRequest {

    private String userName;
    private String userEmail;
    private String password;
    private LocalDate regDate;

    public UserJoinRequest(String userName, String userEmail, String password) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.regDate = LocalDate.now();
    }

    private UserJoinRequest(UserJoinRequestBuilder userJoinRequestBuilder) {
        this.userName = userJoinRequestBuilder.userName;
        this.userEmail = userJoinRequestBuilder.userEmail;
        this.password = userJoinRequestBuilder.password;
        this.regDate = userJoinRequestBuilder.regDate;
    }

    public static UserJoinRequestBuilder builder() {
        return new UserJoinRequestBuilder();
    }

    public static class UserJoinRequestBuilder {
        private String userName;
        private String userEmail;
        private String password;
        private LocalDate regDate;

        private UserJoinRequestBuilder() {}

        public UserJoinRequestBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserJoinRequestBuilder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public UserJoinRequestBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserJoinRequestBuilder regDate(LocalDate regDate) {
            this.regDate = regDate;
            return this;
        }

        public UserJoinRequest build() {
            return new UserJoinRequest(this);
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public User toEntity() {
        return User.builder()
                .userName(userName)
                .userEmail(userEmail)
                .password(password)
                .regDate(regDate)
                .build();
    }

    @Override
    public String toString() {
        return "UserJoinRequest{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}
