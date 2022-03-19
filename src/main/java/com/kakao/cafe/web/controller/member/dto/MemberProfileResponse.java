package com.kakao.cafe.web.controller.member.dto;

import com.kakao.cafe.core.domain.member.Member;

import java.time.LocalDateTime;

public class MemberProfileResponse {
    private Integer id;
    private String nickName;
    private String email;
    private LocalDateTime createAt;

    public MemberProfileResponse(Integer id, String nickName, String email, LocalDateTime createAt) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.createAt = createAt;
    }

    public MemberProfileResponse(Member member) {
        this.id = member.getId();
        this.nickName = member.getNickName();
        this.email = member.getEmail();
        this.createAt = member.getCreateAt();
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
}
