package com.kakao.cafe.controller.dto;

import com.kakao.cafe.domain.User;
import java.time.LocalDateTime;

public class UserDto {

    private String userId;
    private String email;
    private LocalDateTime createdAt;

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
