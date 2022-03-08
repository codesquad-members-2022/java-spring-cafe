package com.kakao.cafe.domain.user;

public class User {

    private Long id;
    private String userId;
    private String password;
    private String email;
    private String username;

    public User(String userId, String password, String email, String username) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.username = username;
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
