package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.List;

public interface UserRepository {
    User findById(Long id);
    List<User> findAll();
    User save(User user);
    void update(Long id, User updateParam);
    void clearStore();
}
