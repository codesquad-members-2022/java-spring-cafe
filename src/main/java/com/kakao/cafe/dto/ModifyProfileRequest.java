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

    public ModifyProfileRequest(int index, String userId, String password, String nowPassword,
                                String newPassword, String name, String email) {

        this.index = index;
        this.userId = userId;
        this.password = password;
        this.nowPassword = nowPassword;
        this.newPassword = newPassword;
        this.name = name;
        this.email = email;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
