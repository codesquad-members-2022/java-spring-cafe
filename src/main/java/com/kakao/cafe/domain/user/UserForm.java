package com.kakao.cafe.domain.user;

public class UserForm {

    private String userId;
    private String password;
    private String email;
    private String username;

    public UserForm(String userId, String password, String email, String username) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.username = username;
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

    public String getUsername() {
        return username;
    }
}
