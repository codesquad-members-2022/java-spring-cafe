package com.kakao.cafe.domain;

public class User {

    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void updateUserInformation(User user) {
        name = user.getName();
        email = user.getEmail();
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

    public boolean hasSameUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean hasSamePassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User{" +
            "userId='" + userId + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
