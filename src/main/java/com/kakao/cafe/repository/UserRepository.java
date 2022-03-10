package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.List;

public interface UserRepository {

    void save(User user);

    User findByUserId(String userId);

    List<User> findAll();

    boolean isExistUserId(String userId);

    boolean isExistEmail(String email);
}
