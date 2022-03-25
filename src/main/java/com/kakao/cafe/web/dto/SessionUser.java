package com.kakao.cafe.web.dto;

import com.kakao.cafe.constants.LoginConstants;
import com.kakao.cafe.domain.user.User;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

public class SessionUser {

    private final String userId;
    private final String name;
    private final String email;

    public static SessionUser from(User user) {
        return new SessionUser(user.getUserId(), user.getName(), user.getEmail());
    }

    public static SessionUser from(HttpSession httpSession) {
        return (SessionUser) httpSession.getAttribute(LoginConstants.SESSIONED_USER);
    }

    public SessionUser(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public boolean hasSameId(String userId) {
        return this.userId.equals(userId);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionUser that = (SessionUser) o;
        return getUserId().equals(that.getUserId()) && getName().equals(that.getName()) && getEmail().equals(that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getName(), getEmail());
    }
}
