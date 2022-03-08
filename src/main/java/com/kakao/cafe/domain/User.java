package com.kakao.cafe.domain;

import java.util.Objects;

public class User {

    private int index;
    private String userId;
    private String password;
    private String name;
    private String email;

    public User() {}

    private User(Builder builder) {
        this.index = builder.index;
        this.userId = builder.userId;
        this.password = builder.password;
        this.name = builder.name;
        this.email = builder.email;
    }

    public int getIndex() {
        return index;
    }

    public String getUserId() {
        return userId;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static Builder builder(String userId) {
        return new Builder(userId);
    }

    public static class Builder {

        private int index;
        private String userId;
        private String password;
        private String name;
        private String email;

        public Builder(String userId) {
            this.userId = userId;
        }

        public Builder index(int index) {
            this.index = index;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
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
