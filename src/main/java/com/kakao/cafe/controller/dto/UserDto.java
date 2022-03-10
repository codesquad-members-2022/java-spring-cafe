package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.User;
import java.time.LocalDateTime;

public class UserDto {

    private String nickname;
    private String email;
    private LocalDateTime createdAt;

    public UserDto(User user) {
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
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
