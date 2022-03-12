package com.kakao.cafe.users.controller.dto;

import com.kakao.cafe.users.domain.User;

public class UserProfile {

    private final Long id;
    private final String userId;
    private final String name;
    private final String email;

    public UserProfile(Long id, String userId, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public static UserProfile of(User user) {
        return new UserProfile(
                user.getId(),
                user.getUserId(),
                user.getName(),
                user.getEmail()
        );
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
