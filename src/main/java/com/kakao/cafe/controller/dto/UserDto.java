package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.User;
import java.time.LocalDateTime;

public class UserDto {

    private Long id;
    private String nickname;
    private String email;
    private LocalDateTime createdAt;

    public UserDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
