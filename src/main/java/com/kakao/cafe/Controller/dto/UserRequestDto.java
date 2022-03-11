package com.kakao.cafe.Controller.dto;

import com.kakao.cafe.domain.User;

public class UserRequestDto {

    private String userId;
    private String password;
    private String name;
    private String email;

    public UserRequestDto() {
    }

    public UserRequestDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static UserRequestDto from(User user) {
        return new UserRequestDto(user.getUserId(),
                user.getPassword(),
                user.getName(),
                user.getEmail());
    }

    public static User toEntity(UserRequestDto userRequestDto) {
        return new User(userRequestDto.getUserId(),
                userRequestDto.getPassword(),
                userRequestDto.getName(),
                userRequestDto.getEmail());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
