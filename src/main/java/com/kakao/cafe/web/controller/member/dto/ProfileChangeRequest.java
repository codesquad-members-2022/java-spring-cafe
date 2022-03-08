package com.kakao.cafe.web.controller.member.dto;

import com.kakao.cafe.core.domain.member.Member;

import java.time.LocalDateTime;

public class ProfileChangeRequest {

    private int id;
    private String nickName;
    private String email;
    private LocalDateTime createAt;

    public ProfileChangeRequest(int id, String nickName, String email, LocalDateTime createAt) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.createAt = createAt;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void enrollInformation(Member member) {
        this.id = member.getId();
        this.nickName = member.getNickName();
        this.email = member.getEmail();
        this.createAt = member.getCreateAt();
    }
}
