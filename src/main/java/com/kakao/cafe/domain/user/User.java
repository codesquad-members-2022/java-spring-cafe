package com.kakao.cafe.domain.user;

public class User {
    private Long id;
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

    public User(Long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean isTheSameId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean isMatchPassword(String password) {
        return this.password.equals(password);
    }

    private boolean isMatchUserId(String userId) {
        return this.userId.equals(userId);
    }

    public User update(User user) {
        if (!isMatchUserId(user.getUserId())) {
            throw new IllegalArgumentException("수정하려는 유저 정보가 일치하지 않습니다.");
        }
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        return this;
    }

    public Long getId() {
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

    public void setId(Long id) {
        this.id = id;
    }
}
