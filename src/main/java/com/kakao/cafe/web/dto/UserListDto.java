package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.User;

public class UserListDto {

    private int userNum;
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public UserListDto(User user, int userNum) {
        userId = user.getId();
        password = user.getPassword();
        name = user.getName();
        email = user.getEmail();
        this.userNum = userNum;
    }

    public static UserListDto from(User user, int userNum) {
        return new UserListDto(user, userNum);
    }

    public String getUserId() {
        return userId;
    }
}
