package com.kakao.cafe.domain;

import com.kakao.cafe.domain.dto.UserCreateDto;

import java.time.LocalDateTime;

public class User {

    private Long id;
    private String userId;
    private String password;
    private String name;
    private String email;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;

    public User(Long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.created_date = LocalDateTime.now();
        this.updated_date = LocalDateTime.now();
    }

    public User(Long id, UserCreateDto userCreateDto) {
        this.id = id;
        this.userId = userCreateDto.getUserId();
        this.password = userCreateDto.getPassword();
        this.name = userCreateDto.getName();
        this.email = userCreateDto.getEmail();
        this.created_date = LocalDateTime.now();
        this.updated_date = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public LocalDateTime getUpdated_date() {
        return updated_date;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", created_date=" + created_date +
                ", updated_date=" + updated_date +
                '}';
    }
}
