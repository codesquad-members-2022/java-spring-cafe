package com.kakao.cafe;

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

    public String getUserId() {
        return userId;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

}
