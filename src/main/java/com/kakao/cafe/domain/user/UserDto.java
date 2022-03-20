package com.kakao.cafe.domain.user;

public class UserDto {

    private String userId;
    private String password;
    private String email;
    private String name;

    public UserDto(String userId, String password, String email, String name) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public User convertToUser() {
        return new User(userId, password, email, name);
    }
}
