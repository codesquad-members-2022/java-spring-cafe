package com.kakao.cafe.web.controller.member.dto;

import com.kakao.cafe.core.domain.member.Member;

import java.time.LocalDateTime;

public class ProfileFormRequest {
    private Long id;
    private String nickName;
    private String email;
    private LocalDateTime createAt;

    public ProfileFormRequest(String nickName, String email) {
        this.nickName = nickName;
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Member toEntity() {
        return new Member(email, nickName);
    }
}
