package com.kakao.cafe.users.controller.dto;

import com.kakao.cafe.users.domain.User;

import java.time.LocalDate;

public class UserResponseDto {

    private final Long id;
    private final String userId;
    private final String name;
    private final String email;
    private final LocalDate createdDate;

    private UserResponseDto(Long id, String userId, String name, String email, LocalDate createdDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
    }

    public static UserResponseDto of(User user) {
        return new UserResponseDto(
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

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
}
