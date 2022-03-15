package com.kakao.cafe.dto;

import com.kakao.cafe.domain.User;
import com.kakao.cafe.exception.user.UnMatchedPasswordException;
import org.springframework.http.HttpStatus;

import static com.kakao.cafe.message.UserDomainMessage.UNMATCHED_PASSWORD_MESSAGE;

public class ModifiedUserParam {

    private int id;
    private final String userId;
    private final String password;
    private final String nowPassword;
    private final String newPassword;
    private final String name;
    private final String email;

    public ModifiedUserParam(int id, String userId, String password, String nowPassword,
                                String newPassword, String name, String email) {

        this.id = id;
        this.userId = userId;
        this.password = password;
        this.nowPassword = nowPassword;
        this.newPassword = newPassword;
        this.name = name;
        this.email = email;
    }

    public void setIndex(int id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getNowPassword() {
        return nowPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void isValidRequest() {
        if (!nowPassword.equals(password)) {
            throw new UnMatchedPasswordException(HttpStatus.OK, UNMATCHED_PASSWORD_MESSAGE);
        }
    }

    public User convertToUser() {
        return new User(id, userId, newPassword, name, email);
    }

    @Override
    public String toString() {
        return "ModifiedUserParam{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", nowPassword='" + nowPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
