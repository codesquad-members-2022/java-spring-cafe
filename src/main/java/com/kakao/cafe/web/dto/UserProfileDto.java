package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.User;

public class UserProfileDto {
    private final String userId;
    private final String email;

    public UserProfileDto(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}
