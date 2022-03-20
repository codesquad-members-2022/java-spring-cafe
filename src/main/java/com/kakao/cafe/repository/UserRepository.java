package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.List;

public interface UserRepository {

    User save(User user);

    User findByUserId(String userId);

    List<User> findAll();

    User findByUserIdAndPassword(String userId, String password);

    boolean isExistUserId(String userId);

    boolean isExistEmail(String email);
}
