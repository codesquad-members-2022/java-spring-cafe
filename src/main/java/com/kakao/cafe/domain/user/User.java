package com.kakao.cafe.domain.user;

public class User {

    private final String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }
    
    public boolean isYourPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isYourId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean checkIfTheIDIsTheSame(User user) {
        return this.userId.equals(user.userId);
    }

    public void updateInfo(User updateUser) {
        this.password = updateUser.password;
        this.name = updateUser.name;
        this.email = updateUser.email;
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

    public String getPassword() {
        return password;
    }
}
