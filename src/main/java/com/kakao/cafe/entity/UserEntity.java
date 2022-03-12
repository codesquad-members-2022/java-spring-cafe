package com.kakao.cafe.entity;

import com.kakao.cafe.domain.Article;
import com.kakao.cafe.domain.User;

public class UserEntity {

    private long id;
    private String userId;
    private String password;
    private String name;
    private String email;

    UserEntity() {}

    public UserEntity(long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static UserEntity of(User user) {
        return new UserEntity(user.getId(), user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User convertToUser() {
        return new User(id, userId, password, name, email);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
