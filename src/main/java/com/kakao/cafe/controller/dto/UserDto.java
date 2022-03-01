package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.User;
import java.time.LocalDateTime;

public class UserDto {

    private String nickname;
    private String email;
    private LocalDateTime createdAt;

    public UserDto(String nickname, String email, LocalDateTime createdAt) {
        this.nickname = nickname;
        this.email = email;
        this.createdAt = createdAt;
    }

    public static UserDto from(User user) {
        return new UserDto(user.getNickname(), user.getEmail(), user.getCreatedAt());
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
