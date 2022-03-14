package com.kakao.cafe.users.controller.dto;

import com.kakao.cafe.users.domain.User;

public class UserProfile {

    private Long id;
    private String userId;
    private String name;
    private String email;

    // Java bean 규약에 따라, default 생성자 제공
    public UserProfile() { }

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

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
