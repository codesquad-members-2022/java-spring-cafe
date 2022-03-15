package com.kakao.cafe.repository;

import com.kakao.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByName(String name);
    List<User> findAll();
    User update(User savedUser, User newUser);
    void delete(User user);
    void deleteAll();
}
