package com.kakao.cafe.web.dto;

import com.kakao.cafe.domain.User;

public class UserListDto {

    private Long userNum;
    private String userId;
    private String password;
    private String name;
    private String email;

    public UserListDto(User user) {
        this.userNum = user.getUserNum();
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
