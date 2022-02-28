package com.kakao.cafe.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String userId;
    private String password;
    private String name;
    private String email;


}
