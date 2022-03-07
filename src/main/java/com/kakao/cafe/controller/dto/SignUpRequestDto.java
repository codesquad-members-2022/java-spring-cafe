package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.User;

public class SignUpRequestDto {

    private String email;
    private String nickname;
    private String password;

    public SignUpRequestDto(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public User toEntity() {
        return new User(email, nickname, password);
    }
}
