package com.kakao.cafe.domain;

import java.util.Objects;

public class User {

    private int index;
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(int index, String userId, String password, String name, String email) {
        this.index = index;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUserId() {
        return userId;
    }

    public boolean ownerOf(String id) {
        return id.equals(userId);
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
