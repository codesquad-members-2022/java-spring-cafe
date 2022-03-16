package kr.codesquad.cafe.user;

import org.springframework.util.Assert;

public class User {

    private String userId;
    private String password;
    private String name;
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        Assert.hasText(userId, "유저 ID는 공백이어선 안 됩니다.");
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Assert.hasText(password, "패스워드는 공백이어선 안 됩니다.");
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Assert.hasText(name, "유저 이름은 공백이어선 안 됩니다.");
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Assert.hasText(email, "유저 이메일은 공백이어선 안 됩니다.");
        this.email = email;
    }

    public boolean userIdIs(String userId) {
        return userId.equals(this.userId);
    }

    public boolean passwordIs(String password) {
        return password.equals(this.password);
    }

    public boolean nameIs(String name) {
        return name.equals(this.name);
    }

    public boolean emailIs(String email) {
        return email.equals(this.email);
    }
}
