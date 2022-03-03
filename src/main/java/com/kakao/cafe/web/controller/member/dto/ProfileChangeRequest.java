package com.kakao.cafe.web.controller.member.dto;

import java.time.LocalDateTime;

public class ProfileChangeRequest {

    private Long id;
    private String nickName;
    private String email;
    private LocalDateTime createAt;

    public ProfileChangeRequest(Long id, String nickName, String email, LocalDateTime createAt) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.createAt = createAt;
    }

    public ProfileChangeRequest(String nickName, String email) {
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
}
