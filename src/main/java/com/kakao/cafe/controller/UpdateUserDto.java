package com.kakao.cafe.controller;

public class UpdateUserDto {

    private int id;
    private String userId;
    private String password;
    private String name;
    private String email;

    public UpdateUserDto(int id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
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
