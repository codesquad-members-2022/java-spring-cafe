package com.kakao.cafe.dto;

public class UserResponseDto implements UserDtoBuilder {

    private String email;
    private String userId;
    private String name;

    public UserResponseDto() {
    }

    private UserResponseDto(String email, String userId, String name) {
        this.email = email;
        this.userId = userId;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public UserDtoBuilder userEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public UserDtoBuilder userName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public UserDtoBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public UserResponseDto getUserDto() {
        return new UserResponseDto(email, userId, name);
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "email='" + email + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
