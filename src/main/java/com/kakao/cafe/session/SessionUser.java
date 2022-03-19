package com.kakao.cafe.session;

import com.kakao.cafe.domain.User;

public class SessionUser {

    private int id;
    private String userId;
    private String password;
    private String name;
    private String email;

    public SessionUser(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public int getId() {
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

    public boolean ownerOf(String userId) {
        return userId.equals(this.userId);
    }

    public boolean confirmPassword(String password) {
        return password.equals(this.password);
    }

    @Override
    public String toString() {
        return "SessionUser{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
