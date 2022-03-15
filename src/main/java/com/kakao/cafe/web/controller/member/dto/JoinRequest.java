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

    public void enrollEmail(String email) {
        this.email = email;
    }

    public Member toEntity(){
        return new Member(null, this.getEmail(), this.getPassword(), this.getNickName());
    }
}
