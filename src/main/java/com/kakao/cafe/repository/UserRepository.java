package com.kakao.cafe.repository;

import com.kakao.cafe.model.User;

import java.util.List;

public interface UserRepository {
    void save(User user);
    User findById(Long id);
    List<User> findAll();
    boolean delete(Long id);
    void update(Long id, User updateParam);
}
