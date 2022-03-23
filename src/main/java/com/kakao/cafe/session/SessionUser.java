package com.kakao.cafe.session;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.ErrorCode;
import com.kakao.cafe.exception.InvalidRequestException;
import com.kakao.cafe.exception.NotFoundException;
import java.util.Optional;
import javax.servlet.http.HttpSession;

public class SessionUser {

    public static final String SESSION_KEY = "SESSION_USER";

    private Integer userNum;
    private String userId;
    private String password;
    private String name;
    private String email;

    public SessionUser(Integer userNum, String userId, String password, String name,
        String email) {
        this.userNum = userNum;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static SessionUser from(HttpSession session) {
        return (SessionUser) Optional.ofNullable(
                session.getAttribute(SESSION_KEY))
            .orElseThrow(() -> new NotFoundException(ErrorCode.SESSION_NOT_FOUND));
    }

    public static SessionUser from(User user) {
        return new SessionUser(
            user.getUserNum(),
            user.getUserId(),
            user.getPassword(),
            user.getName(),
            user.getEmail()
        );
    }

    public User toEntity() {
        return new User(userNum, userId, password, name, email);
    }

    public Integer getUserNum() {
        return userNum;
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

    public void validate(String userId) {
        if (!this.userId.equals(userId)) {
            throw new InvalidRequestException(ErrorCode.INCORRECT_USER);
        }
    }

    public void checkUserId(String userId) {
        if (!this.userId.equals(userId)) {
            throw new InvalidRequestException(ErrorCode.INVALID_REPLY_WRITER);
        }
    }
}
