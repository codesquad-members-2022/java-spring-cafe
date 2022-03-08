package com.kakao.cafe.domain;

import org.springframework.util.Assert;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        Assert.hasText(userId, "아이디는 빈 값이 들어올 수 없습니다.");
        Assert.hasText(password, "비밀번호는 빈 값이 들어올 수 없습니다.");
        Assert.hasText(name, "이름은 빈 값이 들어올 수 없습니다.");
        Assert.hasText(email, "이메일은 빈 값이 들어올 수 없습니다.");

        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }
}
