package com.kakao.cafe.domain.users;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {
    private String userId;
    private String name;
    private String email;

    @Builder
    public UserDto(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}
