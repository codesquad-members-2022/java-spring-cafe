package com.kakao.cafe.web.controller.member.dto;

import com.kakao.cafe.core.domain.member.Member;

public class JoinResponse {
    private String email;
    private String nickName;

    public JoinResponse(Member member) {
        this.email = member.getEmail();
        this.nickName = member.getNickName();
    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
