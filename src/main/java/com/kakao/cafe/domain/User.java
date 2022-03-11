package com.kakao.cafe.domain;

public class User {
    private String userId;
    private String name;
    private String password;
    private String email;
    private int index;

    public User(String userId, String name, String password, String email) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index+1;
    }

    public boolean compareById(String userId){
        return this.userId.equals(userId);
    }
}
