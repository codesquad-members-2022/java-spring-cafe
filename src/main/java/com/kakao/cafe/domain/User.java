package com.kakao.cafe.domain;

public class User {

    private long userIdx;
    private final String userName;
    private final String userPassword;
    private final String userEmail;

    public User(String userName, String userPassword, String userEmail) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }

    public long getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(long userIdx) {
        this.userIdx = userIdx;
    }

    public String getUserName() {
        return userName;
    }
}
