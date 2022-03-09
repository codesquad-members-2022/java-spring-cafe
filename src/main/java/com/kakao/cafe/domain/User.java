package com.kakao.cafe.domain;

public class User {

    private int id;
    private String nickname;
    private String email;
    private String date;
    private String password;

    //TODO : User 안에서 matchesId() 같은걸 만들어서 직접 필드 값을 내보내지 않도록 하자

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
