package com.kakao.cafe.dto;

import com.kakao.cafe.exception.user.UnMatchedPasswordException;
import org.springframework.http.HttpStatus;

import static com.kakao.cafe.message.UserDomainMessage.UNMATCHED_PASSWORD_MESSAGE;

public class ModifiedUserParam {

    private int id;
    private String userId;
    private String password;
    private String nowPassword;
    private String newPassword;
    private String name;
    private String email;

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

    public void switchPassword() {
        password = newPassword;
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
