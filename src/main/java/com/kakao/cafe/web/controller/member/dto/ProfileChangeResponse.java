package com.kakao.cafe.web.controller.member.dto;

import java.time.LocalDateTime;

public class ProfileChangeResponse {

    private Long id;
    private String nickName;
    private String email;
    private LocalDateTime createAt;

    public ProfileChangeResponse(ProfileChangeRequest request) {
        this.id = request.getId();
        this.nickName = request.getNickName();
        this.email = request.getEmail();
        this.createAt = request.getCreateAt();
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
