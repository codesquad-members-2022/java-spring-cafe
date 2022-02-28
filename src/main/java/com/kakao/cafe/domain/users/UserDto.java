package com.kakao.cafe.domain.users;

public class UserDto {
    private int number;
    private String userId;
    private String name;
    private String email;

    public UserDto(int number, String userId, String name, String email) {
        this.number = number;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public int getNumber() {
        return number;
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
