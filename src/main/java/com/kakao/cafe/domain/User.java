package com.kakao.cafe.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class User {

    private int id;
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(@JsonProperty("id") int id, @JsonProperty("userId") String userId,
                @JsonProperty("password") String password, @JsonProperty("name") String name,
                @JsonProperty("email") String email) {

        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
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
