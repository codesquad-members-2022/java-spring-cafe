package com.kakao.cafe.users.controller.dto;

import com.kakao.cafe.users.domain.User;

import java.time.LocalDate;

public class UserResponse {

    private Long id;
    private String userId;
    private String name;
    private String email;
    private LocalDate createdDate;

    // Java bean 규약에 따라, default 생성자 제공
    public UserResponse() { }

    public UserResponse(Long id, String userId, String name, String email, LocalDate createdDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
    }

    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId(),
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedDate().toLocalDate()
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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
