package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.List;

public interface UserRepository {
    User findByUserId(String userId);
    List<User> findAll();
    User save(User user);
    void update(String userId, User updateParam);
    void clearStore();
}
