package com.kakao.cafe.domain;

public class User {

    private Long id;
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(UserJoinRequest userForm) {
        this.userId = userForm.getUserId();
        this.password = userForm.getPassword();
        this.name = userForm.getName();
        this.email = userForm.getEmail();
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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
