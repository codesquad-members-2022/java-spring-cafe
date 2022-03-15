package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.User;

public class UserUpdateFormDto {
    private final String userId;
    private final String oldPassword;
    private final String newPassword;
    private final String name;
    private final String email;

    public UserUpdateFormDto(String userId, String oldPassword, String newPassword, String name, String email) {
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPreviousPassword() {
        return oldPassword;
    }

    public User toEntity() {
        return new User.UserBuilder(userId,newPassword)
            .setName(name)
            .setEmail(email)
            .build();
    }

    @Override
    public String toString() {
        return "UserUpdateFormDto{" +
            "userId='" + userId + '\'' +
            ", oldPassword='" + oldPassword + '\'' +
            ", newPassword='" + newPassword + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
