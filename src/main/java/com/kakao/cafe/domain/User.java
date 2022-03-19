package com.kakao.cafe.domain;

import com.kakao.cafe.controller.UserForm;

import java.util.Objects;

public class User {

    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User(UserForm form) {
        userId = form.getUserId();
        password = form.getPassword();
        name = form.getName();
        email = form.getEmail();
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

    public boolean isCorrectId(String inputId) {
        if (inputId.equals(userId)) {
            return true;
        }
        return false;
    }

    public boolean isCorrectPassword(String inputPassword) {
        if (inputPassword.equals(password)) {
            return true;
        }
        return false;
    }

    public boolean isCorrectName(String inputName) {
        if (inputName.equals(name)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId)
                && password.equals(user.password)
                && name.equals(user.name)
                && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, name, email);
    }
}
