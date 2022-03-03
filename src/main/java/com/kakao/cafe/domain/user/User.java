package com.kakao.cafe.domain.user;

import com.kakao.cafe.web.dto.UserDto;

public class User {

    private String userId;
    private String password;
    private String name;
    private String email;

    public User() {}

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User(UserDto userDto) {
        this.userId = userDto.getUserId();
        this.password = userDto.getPassword();
        this.name = userDto.getName();
        this.email = userDto.getEmail();
    }

    public boolean isSameId(String id) {
        return this.userId.equals(id);
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

}
