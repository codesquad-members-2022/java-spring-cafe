package com.ttasjwi.cafe.controller;

import com.ttasjwi.cafe.domain.user.User;

import java.time.LocalDate;

public class UserResponse {

    private String userName;
    private String userEmail;
    private LocalDate regDate;

    public UserResponse(User user) {
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.regDate = user.getRegDate();
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LocalDate getRegDate() {
        return regDate;
    }
}
