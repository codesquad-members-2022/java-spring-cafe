package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;

import java.util.List;

public interface UserService {
    String join(User user);

    List<User> findUsers();
}
