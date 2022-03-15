package com.kakao.cafe.web.controller.member.dto;

import com.kakao.cafe.core.domain.member.Member;

public class JoinRequest {
    private String email;
    private String password;
    private String nickName;

    public JoinRequest(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public Member toEntity() {
        return new Member(Integer.MIN_VALUE, this.getEmail(), this.getPassword(), this.getNickName());
    }
}
