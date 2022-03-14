package com.kakao.cafe.dto;

public class UserResponse {

    private Integer userNum;
    private String userId;
    private String password;
    private String name;
    private String email;

    private UserResponse() {
    }

    public UserResponse(Integer userNum, String userId, String password, String name,
        String email) {
        this.userNum = userNum;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserResponse user = (UserResponse) o;

        return userId.equals(user.userId)
            && password.equals(user.password)
            && name.equals(user.name)
            && email.equals(user.email);
    }

    @Override
    public String toString() {
        return "UserResponse{" +
            "userNum=" + userNum +
            ", userId='" + userId + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
