package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.User;

public class UserListDto {

    private int userNum;
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public UserListDto(User user) {
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public int getUserNum() {
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
}
