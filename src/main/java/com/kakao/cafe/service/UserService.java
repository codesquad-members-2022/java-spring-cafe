package com.kakao.cafe.service;

import com.kakao.cafe.domain.User;

import java.util.List;

public interface UserService {

    List<User> searchAll();

    User add(User user);

    User update(User user);

    User search(String id);
}
