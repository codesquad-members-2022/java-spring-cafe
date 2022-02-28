package com.kakao.cafe.core.domain.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Member {

    @Id
    private Long id;

    @Column(name = "name")
    private String nickName;
}
