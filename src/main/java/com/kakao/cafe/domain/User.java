package com.kakao.cafe.domain;

import java.util.Objects;

public class User {

    private Long id;
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    // Domain이 DTO에 의존적이지만 기억하기위해 변경 X, Article는 변경
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    public boolean isSameId(String referUserId) {
        return Objects.equals(userId, referUserId);
    }
}
