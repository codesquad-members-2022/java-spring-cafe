package com.kakao.cafe.domain;

import com.kakao.cafe.exception.ErrorMessage;

public class User {

    private int id;
    private String nickname;
    private String email;
    private String date;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDate(String date) {
        this.date = date;
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

    public boolean matchesId(int id) {
        return this.id == id;
    }

    public boolean matchesNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    public boolean matchesEmail(String email) {
        return this.email.equals(email);
    }

    public void checkBlankInput() {
        if (this.nickname.isBlank() || this.email.isBlank() || this.password.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.BLANK_INPUT.get());
        }
    }
}
