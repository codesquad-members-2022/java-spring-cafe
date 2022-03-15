package com.kakao.cafe.domain;

import java.time.LocalDate;

import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.UserException;

public class User {

    private int id;
    private String nickname;
    private String email;
    private LocalDate date;
    private String password;

    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void updateProfile(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
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
            throw new UserException(ErrorCode.BLANK_INPUT);
        }
    }
}
