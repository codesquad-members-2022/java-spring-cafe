package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User addUser(User user);
    User findUser(String id);
}
