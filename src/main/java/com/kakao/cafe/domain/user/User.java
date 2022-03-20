package com.kakao.cafe.domain.user;

public class User {

    private String userId;
    private String password;
    private String email;
    private String name;

    public User(String userId, String password, String email, String name) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void updateUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserDto convertToUserDto() {
        return new UserDto(userId, password, email, name);
    }
}
