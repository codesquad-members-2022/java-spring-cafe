package com.kakao.cafe.users.controller.dto;

public class UserJoinRequest {
    private String userId;
    private String passwd;
    private String name;
    private String email;

    // Java bean 규약에 따라, default 생성자 제공
    public UserJoinRequest() { }

    public UserJoinRequest(String userId, String passwd, String name, String email) {
        this.userId = userId;
        this.passwd = passwd;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
