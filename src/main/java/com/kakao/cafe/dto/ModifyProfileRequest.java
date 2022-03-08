package com.kakao.cafe.dto;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.user.UnMatchedPasswordException;

import static com.kakao.cafe.message.UserMessage.UNMATCHED_PASSWORD_MESSAGE;

public class ModifyProfileRequest {

    private int index;
    private String userId;
    private String password;
    private String nowPassword;
    private String newPassword;
    private String name;
    private String email;

    public void setIndex(int index) {
        this.index = index;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNowPassword(String nowPassword) {
        this.nowPassword = nowPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void isValidRequest() {
        if (!nowPassword.equals(password)) {
            throw new UnMatchedPasswordException(UNMATCHED_PASSWORD_MESSAGE);
        }
    }

    public User convertToUser() {
        return User.builder(userId).password(newPassword).name(name).email(email).index(index).build();
    }
}
